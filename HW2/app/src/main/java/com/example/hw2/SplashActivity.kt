package com.example.hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw2.util.startActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    private val delay : Long = 3 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Starting of the TestActivity is delayed by delay (whatever the amount is)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity<TestActivity>()
                finish()
            }
        }, delay)
    }
}