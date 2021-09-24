package com.example.hw4.utils

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Fragment.toast(messageToShow: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), messageToShow, duration).show()
}

fun AppCompatActivity.toast(messageToShow: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, messageToShow, duration).show()
}

fun Fragment.saveDataAsString(key: String, value: String) {
    val sharedPreferences: SharedPreferences =
        requireActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE)
    val myEdit = sharedPreferences.edit()
    myEdit.putString(key, value)
    myEdit.apply()
}

fun AppCompatEditText.getString(): String {
    return this.text.toString()
}