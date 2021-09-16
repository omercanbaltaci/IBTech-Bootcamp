package com.example.hw3.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.hw3.MainActivity
import com.example.hw3.R

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

        // Basic functionality to show or hide BottomNavigationView
        val mainActivity = activity as MainActivity
        if (isNavigationbarVisible())
            mainActivity.showNavigation()
        else
            mainActivity.hideNavigation()
    }

    abstract fun getLayoutID() : Int

    // Popping up a dialog for the user to ask if they want to exit the app or continue using it
    fun showPopUp() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext())
                    .setTitle("Bilgi")
                    .setMessage("Uygulamadan çıkmak ister misiniz?")
                    .setCancelable(false)
                    .setPositiveButton("Evet"
                    ) { _, _ -> activity?.finish() }
                    .setNegativeButton("Hayır", null)
                    .show()
            }
        })
    }

    // Function to change the statusbar color
    fun changeStatusBarColor(id: Int) {
        activity?.window?.statusBarColor = resources.getColor(id)
    }
}