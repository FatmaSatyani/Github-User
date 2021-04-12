package com.fatmasatyani.githser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fatmasatyani.githser.DetailActivity
import com.fatmasatyani.githser.R
import com.fatmasatyani.githser.adapter.GithubAdapter
import com.fatmasatyani.githser.adapter.SectionPagerAdapter
import com.fatmasatyani.githser.databinding.MyProfileFragmentBinding
import com.fatmasatyani.githser.entity.Github
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.fatmasatyani.githser.BuildConfig
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MyProfileFragment: Fragment() {

    private lateinit var binding: MyProfileFragmentBinding
    private var dimen = 460

//    companion object {
//        const val EXTRA_GITHUB = "extra_github"
//    }

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = MyProfileFragmentBinding.inflate(inflater,container,false)

//        val dataGithub: Github? = arguments?.getParcelable(EXTRA_GITHUB)
//        val username = "${dataGithub?.username}"
        val username = "fatmasatyani"

        val sectionsPagerAdapter = SectionPagerAdapter(requireActivity(), username)
        val viewPager: ViewPager2 = binding.viewPager
        binding.viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tablayout
        TabLayoutMediator(tabs,viewPager) {tab, position ->
            tab.text = resources.getString(DetailActivity.TAB_TITLES[position])
        }.attach()

        getUser(username)

        return binding.root
    }

    private fun getUser(username: String) {
        binding.progressBar.visibility = View.VISIBLE

        val user = AsyncHttpClient()

        val url =  "${BuildConfig.BASE_URL}/users/fatmasatyani"
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
                    val user = Github()
                    user.name = name
                    user.username = username
                    user.location = location
                    user.company = company
                    user.repository = repository
                    user.followers = follower
                    user.following = following
                    user.avatar = avatar

                    binding.apply {
                        Glide.with(this@MyProfileFragment)
                            .load(user.avatar)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .apply(RequestOptions().override(dimen, dimen))
                            .into(imgUserPhoto)
                        txtUserName.text = "${user.name}"
                        txtUserId.text = "${user.username}"
                        txtUserLocation.text = "${user.location}"
                        txtUserCompany.text = "${user.company}"
                        txtUserRepository.text = "${user.repository} Repositories"
                        tbFollowerNumb.text = "${user.followers}"
                        tbFollowingNumb.text = "${user.following}"

                        showLoading(false)
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}