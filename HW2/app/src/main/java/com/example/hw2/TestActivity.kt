package com.example.hw2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.hw2.util.inTransaction

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // An extension function to push a fragment have been used here
        supportFragmentManager.inTransaction {
            add(R.id.flFragment, TestFragment())
        }
    }
}