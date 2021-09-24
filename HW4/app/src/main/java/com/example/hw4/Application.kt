package com.example.hw4

import android.app.Application
import com.example.hw4.service.ServiceConnector

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        ServiceConnector.init()
    }
}