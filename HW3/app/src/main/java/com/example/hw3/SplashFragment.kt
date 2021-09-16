package com.example.hw3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hw3.base.BaseFragment
import com.example.hw3.util.smoothProgress
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : BaseFragment() {
    private val delay: Long = 3 * 1000

    // We do not show the navigation bar in SplashFragment
    override fun isNavigationbarVisible(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Delaying the opening of the first fragment
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_battleFragment)
        }, delay)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearProgressIndicator.smoothProgress(100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeStatusBarColor(R.color.splash_background)
    }

    override fun getLayoutID(): Int = R.layout.fragment_splash
}