package com.example.hw3.util

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.google.android.material.progressindicator.LinearProgressIndicator

// Two functions below is to shorten the amount of code written to change visibility of views
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

// Smoothly filling up the linear progress indicator in 3000 ms
fun LinearProgressIndicator.smoothProgress(percent: Int) {
    val animation = ObjectAnimator.ofInt(this, "progress", percent)
    animation.duration = 3000
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}