package com.example.hw3.base

interface FragmentInterface {
    // Whatever the value a fragment gets at the start, it will determine if BNV will be shown or not
    fun isNavigationbarVisible() = true

    // Showing a pop up to the user if they want to exit or keep using the app
    fun showPopUp()

    // Function to change the statusbar color
    fun changeStatusBarColor(id: Int)
}