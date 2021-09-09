package com.example.hw2

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hw2.util.COUNTDOWN_LIMIT
import com.example.hw2.util.INCREMENT_PROGRESS
import com.example.hw2.util.placeExtraAnswers
import com.example.hw2.util.placeExtraWord
import kotlin.concurrent.fixedRateTimer

class QuestionActivity : AppCompatActivity() {
    private var progressInSeconds: Int = 100
    private val delay: Long = 1000
    private var count: Int = 19

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        this.window.statusBarColor = resources.getColor(R.color.question_color)

        // Below two extensions functions have been used in order to populate textviews
        findViewById<TextView>(R.id.word_in_question).placeExtraWord(intent)
        findViewById<TextView>(R.id.answer1).placeExtraAnswers(intent, 0)
        findViewById<TextView>(R.id.answer2).placeExtraAnswers(intent, 1)
        findViewById<TextView>(R.id.answer3).placeExtraAnswers(intent, 2)
        findViewById<TextView>(R.id.answer4).placeExtraAnswers(intent, 3)

        // This is where the countdown timer for word questions tick
        updateCountdownTimer()
        fixedRateTimer("timer", false, 0, delay) {
            this@QuestionActivity.runOnUiThread {
                progressInSeconds -= INCREMENT_PROGRESS         // Decrement by 5, 20 seconds, equals to 100
                updateCountdownTimer()

                // Updating countdown condition
                if (count >= COUNTDOWN_LIMIT) updateCountdownTime(count)
                count--
            }
        }
    }

    // Function to basically refresh the progressbar
    private fun updateCountdownTimer() {
        val cdTimer = findViewById<ProgressBar>(R.id.countdown_timer)
        cdTimer.progress = progressInSeconds
    }

    // Refreshing the countdown
    private fun updateCountdownTime(count: Int) {
        val cdTime = findViewById<TextView>(R.id.cdTime)
        cdTime.text = count.toString()
    }
}