package com.example.hw4.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw4.R
import com.example.hw4.model.User
import com.example.hw4.service.BaseCallBack
import com.example.hw4.service.ServiceConnector
import com.example.hw4.utils.USER_TOKEN
import com.example.hw4.utils.gone
import com.example.hw4.utils.toast
import com.google.android.material.progressindicator.LinearProgressIndicator

class SplashActivity : AppCompatActivity() {
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        changeStatusBarColor(R.color.splash_background)

        val progressBar = findViewById<LinearProgressIndicator>(R.id.splash_progress_bar)

        if (isLoggedIn()) {
            User.getCurrentInstance().token = token

            ServiceConnector.restInterface.getMe().enqueue(object : BaseCallBack<User>() {
                override fun onSuccess(data: User) {
                    super.onSuccess(data)

                    progressBar.gone()
                    User.getCurrentInstance().setUser(data)

                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    intent.putExtra("state", "home")
                    startActivity(intent)
                    finish()
                }

                override fun onFailure() {
                    super.onFailure()

                    toast("Please authenticate.")
                }
            })
        } else {
            progressBar.gone()

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.putExtra("state", "login")
            startActivity(intent)
            finish()
        }
    }

    private fun isLoggedIn(): Boolean {
        val token = getToken()
        return token.isNotEmpty()
    }

    private fun getToken(): String {
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        token = sh.getString(USER_TOKEN, "")!!

        return token!!
    }

    private fun changeStatusBarColor(id: Int) {
        this.window.statusBarColor = resources.getColor(id)
    }
}