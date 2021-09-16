package com.example.hw3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw3.base.BaseFragment

class MyDecksFragment : BaseFragment() {
    override fun isNavigationbarVisible(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showPopUp()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_decks, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeStatusBarColor(R.color.my_decks_status_bar)
    }

    override fun getLayoutID(): Int = R.layout.fragment_my_decks
}