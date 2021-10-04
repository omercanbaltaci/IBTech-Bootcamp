package com.example.hw5.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hw5.ui.filmlist.model.Film

fun Fragment.showToast(messageToShow: String) {
    Toast.makeText(requireContext(), messageToShow, Toast.LENGTH_LONG).show()
}

fun GridLayoutManager.changeSpanSize(
    list: MutableList<Film>,
    progressDialogSize: Int,
    defaultSize: Int
) {
    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (list[position].viewType == 1) progressDialogSize
            else defaultSize
        }

    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}