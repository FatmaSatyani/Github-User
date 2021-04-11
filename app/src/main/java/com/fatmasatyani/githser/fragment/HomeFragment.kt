package com.fatmasatyani.githser.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatmasatyani.githser.DetailActivity
import com.fatmasatyani.githser.DetailActivity.Companion.EXTRA_GITHUB
import com.fatmasatyani.githser.R
import com.fatmasatyani.githser.adapter.HomeAdapter
import com.fatmasatyani.githser.database.UserDataBase
import com.fatmasatyani.githser.databinding.HomeFragmentBinding
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.repository.UserRepository
import com.fatmasatyani.githser.utils.Status
import com.fatmasatyani.githser.viewmodel.HomeViewModel
import com.fatmasatyani.githser.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    companion object {
        private val TAG = "HomeFragment"
    }

    private lateinit var adapter: HomeAdapter
    private lateinit var binding: HomeFragmentBinding
    private var listUser: List<Github> = arrayListOf()
    private lateinit var navController: NavController

    private val viewModels: HomeViewModel by viewModels {
        val dao = UserDataBase.getInstance(requireContext()).favoriteDao()
        ViewModelFactory(UserRepository(dao))
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = HomeFragmentBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        adapter = HomeAdapter (arrayListOf()) {
//            val moveIntent = Intent (this@HomeFragment,DetailActivity::class.java)
//            (moveIntent.putExtra(DetailActivity.EXTRA_GITHUB,it))
//            startActivity(moveIntent) }
//        adapter.notifyDataSetChanged()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HomeFragmentBinding.inflate(inflater,container,false)

        adapter = HomeAdapter {
            val moveIntent = Intent (requireContext(), DetailActivity::class.java)
            (moveIntent.putExtra(DetailActivity.EXTRA_GITHUB,it))
            startActivity(moveIntent) }
        adapter.notifyDataSetChanged()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initNav(view)
        initSearch()
        observerData()
        observerSearch()
    }

    private fun initList() {
        binding.rvGithub.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter {
            actionToDetail(it)
        }
        binding.rvGithub.layoutManager = LinearLayoutManager(context)
        binding.rvGithub.adapter = adapter
    }

    private fun actionToDetail(github: Github) {
        val bundle = bundleOf(EXTRA_GITHUB to github)
        navController.navigate(
            R.id.action_homeFrag_to_detailActivity,
            bundle
        )
    }

    private fun initNav(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun initSearch() {
        binding.svUser.apply {
//            setOnClickListener {
//                onActionViewExpanded()
//            }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener  {
                override fun onQueryTextSubmit(newText: String?): Boolean {
                    if (newText != null && newText.isNotEmpty()) {
                        viewModels.searchUser(newText)
                    } else {
                        adapter.set(listUser)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
//                    if (!::listUser.isInitialized) return false
                    return true
                }
            })
        }
    }


    private fun observerData() {
        viewModels.user.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "success: ")
                    if (it.data != null) {
                        listUser = it.data
                        it.data.toList().let { it1 -> adapter.set(it1) }
                    }
                    showLoading(false)
                }
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    Log.d(TAG, "error: ")
                    Log.e(TAG, "${it.status},${it.data} and ${it.message}" )
                    showError()
                    showLoading(false)
                }
            }
        }
    }

    private fun observerSearch() {
        viewModels.userSearched.observe(viewLifecycleOwner) {
            Log.d(TAG, "observerSearch ${it.status}, ${it.message} and ${it.data}")
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("<RESULT>", "observerSearch: SUCCESS")
                    if (it.data != null) {
                        Log.d("<RESULT>", "observerSearch: data != null ${it.data.userItems}")
                        adapter.set(it.data.userItems)
//                        it.data.toList().let { it1 -> adapter.set(it1) }
                    }
//                    showNormal()
                }
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    Log.e(TAG, "observerSearch ${it.status}, ${it.data} and ${it.message}")
                    showError()
                    showLoading(false)
                }
            }
        }
    }

//    private fun showNormal() {
//        TODO("Not yet implemented")
//    }

    private fun showError() {
        binding.root.visibility = View.VISIBLE
        binding.rvGithub.visibility = View.GONE
    }

    private fun showLoading(i: Boolean) {
        binding.progressBar.apply {
            visibility = if (i) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

}