package com.example.hw2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class TestFragment : Fragment(R.layout.fragment_test) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beginNow = getView()?.findViewById<CardView>(R.id.begin_now_btn)
        beginNow?.setOnClickListener {
            // If the button has been pressed,
            val intent = Intent(activity, QuestionActivity::class.java).apply {
                putExtra(com.example.hw2.util.KEY1, com.example.hw2.util.WORD)      // Put the word in extra
                putExtra(com.example.hw2.util.KEY2, com.example.hw2.util.ANSWERS)   // Put the answers in extra
            }
            activity?.startActivity(intent)                                         // Start the activity using the ext. func.
        }
    }
}