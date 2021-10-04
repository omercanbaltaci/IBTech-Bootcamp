package com.example.hw5.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hw5.ui.filmlist.FavoriteFilmFragment
import com.example.hw5.ui.filmlist.FilmListFragment

class ChildFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FilmListFragment()
            1 -> FavoriteFilmFragment()
            else -> Fragment()
        }
    }
}