package com.example.hw3.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw3.MainActivity

abstract class BaseFragment : Fragment(), FragmentInterface {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutID(), container, false)
    }

    override fun onResume() {
        super.onResume()

        val mainActivity = activity as MainActivity
        if (isNavigationbarVisible())
            mainActivity.showNavigation()
        else
            mainActivity.hideNavigation()
    }

    abstract fun getLayoutID() : Int
}