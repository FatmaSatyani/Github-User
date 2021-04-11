package com.fatmasatyani.githser.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.entity.UserSearchResponse
import com.fatmasatyani.githser.repository.UserRepository
import com.fatmasatyani.githser.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository): ViewModel() {

    val user: LiveData<Resource<List<Github>>> = user()
    val userSearched = MutableLiveData<Resource<UserSearchResponse>>()
//    private lateinit var adapter: GithubAdapter
//    private lateinit var binding: HomeFragmentBinding
//    private lateinit var listUser: List<Github>
//    private val TAG = "HomeViewModel"

//    fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = HomeFragmentBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        initBottomNav()
//
//        adapter = GithubAdapter(arrayListOf()) {
//            val moveIntent = Intent (this@HomeViewModel, DetailActivity::class.java)
//            (moveIntent.putExtra(DetailActivity.EXTRA_GITHUB,it))
//            startActivity(moveIntent) }
//        adapter.notifyDataSetChanged()
//
//        binding.rvGithub.layoutManager = LinearLayoutManager(this)
//        binding.rvGithub.adapter = adapter
//
//        binding.svUser.setOnClickListener{
//            getUser(binding.svUser.toString())
//        }
//    }
//
//    private fun initBottomNav() {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        NavigationUI.setupWithNavController(binding.bottomNv,navController)
//    }
//
//    private fun getUser(toString: String) {
//
//        binding.progressBar.visibility = View.VISIBLE
//        val listItems = ArrayList<Github>()
//        val user = AsyncHttpClient()
//        val url = " ${BuildConfig.BASE_URL}/search/users?q=" + binding.svUser
//        user.addHeader("Authorization", BuildConfig.GITHUB_KEY)
//        user.addHeader("User-Agent", "request")
//        user.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
//                binding.progressBar.visibility = View.VISIBLE
//
//                try {
//                    val result = String (responseBody)
//                    val responseObject = JSONObject(result)
//                    val list = responseObject.getJSONArray("items")
//                    for (i in 0 until list.length()) {
//                        val githubItems = list.getJSONObject(i)
//                        val username = githubItems.getString("login")
//                        val avatar = githubItems.getString("avatar_url")
//                        val user = Github()
//                        user.username = username
//                        user.avatar = avatar
//                        listItems.add(user)
//                    }
//                    adapter.setData(listItems)
//                    showLoading(false)
//                } catch (e: Exception) {
//                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
//                    e.printStackTrace()
//                }
//                Log.d(TAG, "Gagal ${responseBody} $statusCode")
//            }
//
//            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
//                binding.progressBar.visibility = View.INVISIBLE
//
//                val errorMessage = when (statusCode) {
//                    401 -> "$statusCode : Bad Request"
//                    403 -> "$statusCode : Forbidden"
//                    404 -> "$statusCode : Not Found"
//                    else -> "$statusCode : ${error.message}"
//                }
//                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
//    private fun initSearch() {
//        binding.svUser.apply {
//            setOnClickListener {
//                onActionViewExpanded()
//            }
//            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    if (!::listUser.isInitialized) return false
//                    if (newText !=null && newText.isNotEmpty())
//                    else {
//                        adapter.set(listUser)
//                    }
//                    return true
//                }
//            })
//        }
//    }

//    private fun setOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {
//
//    }
//
//    private fun setOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {
//
//    }
//
//    private fun showLoading(state: Boolean) {
//        if(state) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }

    private fun user() = liveData(Dispatchers.IO)  {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Ouccred!"))
        }
    }

    fun searchUser (query: String) = viewModelScope.launch(Dispatchers.IO) {
        userSearched.postValue((Resource.loading(data = null)))
        try {
            val searchResponse = repository.searchUser(query)
            Log.d("<RESULT>", "try: ${Gson().toJson(searchResponse)}")
            userSearched.postValue(Resource.success(data = searchResponse))
        } catch (exception: Exception) {
            Log.d("<RESULT>", "catch: ${exception.message}")
            userSearched.postValue(
                Resource.error(data = null, message = exception.message?: "Error Occured!")
            )
        }
    }


//    private fun showLoading(state: Boolean) {
//        if(state) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}