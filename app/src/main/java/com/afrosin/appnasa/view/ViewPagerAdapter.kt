package com.afrosin.appnasa.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

const val TODAY_IDX = 0
const val YESTERDAY_IDX = -1
const val DAY_BEFORE_YESTERDAY_IDX = -2

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(
        PictureOfTheDayFragment(TODAY_IDX),
        PictureOfTheDayFragment(YESTERDAY_IDX),
        PictureOfTheDayFragment(DAY_BEFORE_YESTERDAY_IDX)
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            in 0..2 -> fragments[position]
            else -> fragments[TODAY_IDX]
        }
    }
}