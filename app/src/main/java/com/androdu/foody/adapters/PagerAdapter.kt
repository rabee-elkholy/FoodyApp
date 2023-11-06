package com.androdu.foody.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    private val recipeBundle: Bundle,
    private val fragments: ArrayList<Fragment>,
    private val titles: ArrayList<String>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        fragments[position].arguments = recipeBundle
        return fragments[position]
    }

    fun getTabTitle(position: Int): String {
        return titles[position]
    }
}