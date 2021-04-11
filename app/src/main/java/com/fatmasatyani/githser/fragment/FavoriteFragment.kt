package com.fatmasatyani.githser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatmasatyani.githser.DetailActivity
import com.fatmasatyani.githser.R
import com.fatmasatyani.githser.adapter.FavoriteAdapter
import com.fatmasatyani.githser.database.UserDataBase
import com.fatmasatyani.githser.databinding.FavoriteFragmentBinding
import com.fatmasatyani.githser.entity.DataMapper
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.repository.UserRepository
import com.fatmasatyani.githser.viewmodel.FavoriteViewModel
import com.fatmasatyani.githser.viewmodel.ViewModelFactory

class FavoriteFragment: Fragment() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var navController: NavController
    private val viewModel: FavoriteViewModel by viewModels {
        val dao = UserDataBase.getInstance(requireContext()).favoriteDao()
        ViewModelFactory(UserRepository(dao))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initNav(view)
        observerFavorite()
    }

    private fun initList() {
        adapter = FavoriteAdapter {
            actionToDetail(DataMapper.singleFavoriteToUser(it))
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvFavorite.adapter = this.adapter
    }

    private fun actionToDetail(user: Github) {
        val bundle = bundleOf(DetailActivity.EXTRA_GITHUB to user)
        navController.navigate(
            R.id.action_favoriteFrag_to_detailActivity,
            bundle
        )
    }

    private fun initNav(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun observerFavorite() {
        viewModel.favorite.observe(viewLifecycleOwner) {
            adapter.set(it)
        }
    }
}