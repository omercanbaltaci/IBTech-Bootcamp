package com.example.hw4.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw3.base.FragmentInterface

abstract class BaseFragment : Fragment(), FragmentInterface {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutID(), container, false)
    }

    abstract fun getLayoutID(): Int

    override fun changeStatusBarColor(id: Int) {
        activity?.window?.statusBarColor = resources.getColor(id)
    }

    open fun prepareView() {}
}