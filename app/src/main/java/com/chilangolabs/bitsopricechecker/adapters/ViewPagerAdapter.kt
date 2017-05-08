package com.chilangolabs.bitsopricechecker.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Gorro on 07/05/17.
 */

class ViewPagerAdapter(fm: FragmentManager, fragmentList: List<Fragment>) : FragmentStatePagerAdapter(fm) {

    private var fragmentList = listOf<Fragment>()

    init {
        this.fragmentList = fragmentList
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}
