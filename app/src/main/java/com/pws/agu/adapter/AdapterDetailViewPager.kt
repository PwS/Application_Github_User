package com.pws.agu.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pws.agu.R
import com.pws.agu.view.fragment.FragmentFollowers
import com.pws.agu.view.fragment.FragmentFollowing

class AdapterDetailViewPager(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        FragmentFollowing(),
        FragmentFollowers()
    )

    private val tabTitles = intArrayOf(
        R.string.following,
        R.string.follower
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }
}