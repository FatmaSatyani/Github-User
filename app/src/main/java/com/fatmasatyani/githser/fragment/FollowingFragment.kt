package com.fatmasatyani.githser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatmasatyani.githser.BuildConfig
import com.fatmasatyani.githser.adapter.FragmentAdapter
import com.fatmasatyani.githser.databinding.ActivityFollowingFragmentBinding
import com.fatmasatyani.githser.entity.Github
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingFragment : Fragment() {

    private lateinit var adapter: FragmentAdapter
    private lateinit var binding: ActivityFollowingFragmentBinding

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun getUser(username: String) {
        val listItems = ArrayList<Github>()
        val user = AsyncHttpClient()

        val url = "${BuildConfig.BASE_URL}/users/${username}/following"
        user.addHeader("Authorization", "${BuildConfig.GITHUB_KEY}")
        user.addHeader("User-Agent", "request")
        user.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)
                    for (i in 0 until responseObject.length()) {
                        val githubItems = responseObject.getJSONObject(i)
                        val username = githubItems.getString("login")
                        val avatar = githubItems.getString("avatar_url")
                        val user = Github()
                        user.username = username
                        user.avatar = avatar
                        listItems.add(user)
                    }
                    adapter.setData(listItems)
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ActivityFollowingFragmentBinding.inflate(inflater, container, false)

        val username = arguments?.getString(FollowingFragment.ARG_USERNAME)
        if (username != null) {
            getUser(username)
        }

        adapter = FragmentAdapter()
        binding.rvFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFragment.adapter = adapter

        return binding.root
    }
}
