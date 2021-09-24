package com.example.hw4.service

import com.example.hw4.model.User
import com.example.hw4.utils.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceConnector {
    companion object {
        private var retrofit: Retrofit? = null
        lateinit var restInterface: TaskerAPI

        fun init() {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val httpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(AuthenticationInterceptor())
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            restInterface = retrofit?.create(TaskerAPI::class.java)!!
        }
    }
}

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", User.getCurrentInstance().token ?: "")
            .build()

        return chain.proceed(request)
    }
}