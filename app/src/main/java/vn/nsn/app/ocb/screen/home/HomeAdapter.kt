package vn.nsn.app.ocb.screen.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import vn.nsn.app.ocb.mvp.MvpFragment

class HomeAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        const val PAGE_COUNT = 4
    }

    private val fragments = mutableListOf<MvpFragment<*>>()

    init {
//        fragments.add(TopPageFragment())
//        fragments.add(PopularFragment())
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    fun getFragment(position: Int): MvpFragment<*> {
        return fragments[position]
    }
}