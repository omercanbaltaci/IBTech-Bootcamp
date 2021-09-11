package com.example.hw2.util

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

// Extension function to start an activity
inline fun<reified T : AppCompatActivity> Context.startActivity(block : Intent.() -> Unit = {}){
    val intent  = Intent(this , T::class.java)
    startActivity(
        intent.also {
            block.invoke(it)
        }
    )
}

// Extension function to transition into and pushing a fragment
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

// Extension function to place the word in question on the textview
fun TextView.placeExtraWord(intent: Intent) {
    this.apply {
        text = intent.getStringExtra(KEY1)
    }
}

// Extension function to place the answers in question on the textview
fun TextView.placeExtraAnswers(intent: Intent, index: Int) {
    this.apply {
        text = intent.getStringArrayExtra(KEY2)?.get(index).toString()
    }
}
