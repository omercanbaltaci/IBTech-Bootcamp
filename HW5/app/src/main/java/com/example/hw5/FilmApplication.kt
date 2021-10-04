package com.example.hw5

import android.app.Application
import com.example.hw5.network.ServiceConnector

class FilmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceConnector.init()
    }
}