package com.fatmasatyani.githser.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fatmasatyani.githser.R
import com.fatmasatyani.githser.fragment.FollowerFragment
import com.fatmasatyani.githser.fragment.FollowingFragment

class SectionPagerAdapter (activity: FragmentActivity, private val username: String): FragmentStateAdapter(activity) {

    companion object {
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)

        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}