package com.example.hw5.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceConnector {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private var retrofit: Retrofit? = null
        lateinit var restInterface: FilmAPI

        fun init() {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val httpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            restInterface = retrofit?.create(FilmAPI::class.java)!!
        }
    }
}