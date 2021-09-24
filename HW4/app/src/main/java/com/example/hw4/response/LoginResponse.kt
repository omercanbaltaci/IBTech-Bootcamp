package com.example.hw4.response

import com.example.hw4.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user") val user: User,
    val token: String
)