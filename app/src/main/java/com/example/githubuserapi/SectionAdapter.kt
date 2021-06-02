package com.example.githubuserapi

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionAdapter(private val context: Context, fragmentManager: FragmentManager, dataBundle: Bundle):FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragmentBundle: Bundle
    init {
        fragmentBundle = dataBundle
    }
    @StringRes
    private val TAB = intArrayOf(R.string.Followers, R.string.Following)
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = Followers()
            1 -> fragment = Following()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB[position])
    }

}