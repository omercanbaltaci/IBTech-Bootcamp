package com.example.hw3.avatarselection.model

import java.io.Serializable

data class Data(
    val viewType: Int,              // Text or ImageView
    val text: String? = null,       // The text
    val avatar: Int? = null         // Resource ID for the avatar
) : Serializable
