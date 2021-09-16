package com.example.hw3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.hw3.util.gone
import com.example.hw3.util.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment_container)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // Disable the tinting of BNV, to show actual icons
        bottomNavigationView.itemIconTintList = null
    }

    fun hideNavigation() {
        bottomNavigationView.postDelayed({
            bottomNavigationView.gone()
        }, 0)
    }

    fun showNavigation() {
        bottomNavigationView.postDelayed({
            bottomNavigationView.visible()
        }, 0)
    }
}