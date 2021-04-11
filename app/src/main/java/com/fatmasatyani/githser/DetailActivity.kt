package com.fatmasatyani.githser

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fatmasatyani.githser.adapter.SectionPagerAdapter
import com.fatmasatyani.githser.database.UserDataBase
import com.fatmasatyani.githser.databinding.ActivityDetailBinding
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.entity.UserDetail
import com.fatmasatyani.githser.repository.UserRepository
import com.fatmasatyani.githser.viewmodel.DetailViewModel
import com.fatmasatyani.githser.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    private lateinit var userDetail: UserDetail
    private val detailViewModel: DetailViewModel by viewModels {
        val dao = UserDataBase.getInstance(this).favoriteDao()
        ViewModelFactory(UserRepository(dao),getIntentData)
    }

    companion object {
        const val EXTRA_GITHUB = "extra_github"
        private const val TAG = "DetailActivity"

        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveData()
        buttonListener()
        initTabLayout()

        val dataGithub: Github? = intent.getParcelableExtra(EXTRA_GITHUB)
        val username = "${dataGithub?.username}"

        detailViewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.btnFav.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            }
            else {
                binding.btnFav.setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_primary))
            }
        }

        getUser(username)
    }

    private fun initTabLayout() {
        val user = detailViewModel.github.username
        val sectionsPagerAdapter = user?.let { SectionPagerAdapter(this, it) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tablayout)
        TabLayoutMediator(tabs,viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private var getIntentData: Github? = null

    private fun retrieveData() {
        getIntentData = intent.extras?.getParcelable(EXTRA_GITHUB)
    }

    private fun buttonListener() {
        binding.btnFav.setOnClickListener {
            detailViewModel.apply {
                if (isFavorite.value == true) {
                    Log.d("<TEST>", "buttonListener: ")
                    detailViewModel.removeFavorite()
                    showToastFavoriteStatus (false)
                } else {
                    Log.d("<TEST>", "buttonListener: else")
                    if (!:: userDetail.isInitialized) return@setOnClickListener
                    Log.d("<TEST>", "buttonListener: after isInitiaized")
                    detailViewModel.addToFavorite(userDetail)
                    showToastFavoriteStatus(true)
                }
            }
        }
    }

    private fun showToastFavoriteStatus(isFavorite: Boolean) {
        if (isFavorite) {
            Toast.makeText(this@DetailActivity,"Added to Favorite", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@DetailActivity, "Removed from Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUser(username: String) {
        binding.progressBar.visibility = View.VISIBLE

        val listItems = ArrayList<Github>()
        val user = AsyncHttpClient()

        val url =  "${BuildConfig.BASE_URL}/users/" + username
        user.addHeader("Authorization", BuildConfig.GITHUB_KEY)
        user.addHeader("User-Agent","Agent")

        user.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val name = responseObject.getString("name")
                    val username = responseObject.getString("login")
                    val location = responseObject.getString("location")
                    val company = responseObject.getString("company")
                    val repository = responseObject.getInt("public_repos")
                    val follower = responseObject.getInt("followers")
                    val following = responseObject.getInt("following")
                    val avatar = responseObject.getString("avatar_url")
                    val user = UserDetail()
                    user.name = name
                    user.username = username
                    user.location = location
                    user.company = company
                    user.publicRepository = repository
                    user.followers = follower
                    user.following = following
                    user.avatar = avatar

                    userDetail = user

                    binding.apply {
                        Glide.with(this@DetailActivity)
                            .load(user.avatar)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .apply(RequestOptions().override(460, 460))
                            .into(imgUserPhoto)
                        txtUserName.text = "${user.name}"
                        txtUserId.text = "${user.username}"
                        txtUserLocation.text = "${user.location}"
                        txtUserCompany.text = "${user.company}"
                        txtUserRepository.text = "${user.publicRepository} Repositories"
                        txtUserFollower.text = "${user.followers} Followers"
                        txtUserFollowing.text = "${user.following} Following"

                        showLoading(false)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
        Log.d(TAG, "Coba Lagi")
    }

    private fun showLoading(state: Boolean) {
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}