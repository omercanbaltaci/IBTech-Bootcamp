package com.example.hw2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class TestFragment : Fragment(R.layout.fragment_test) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beginNow = getView()?.findViewById<CardView>(R.id.begin_now_btn)
        beginNow?.setOnClickListener {
            val intent = Intent(activity, QuestionActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}