package com.example.hw3

import java.io.Serializable

data class Data (
    val viewType: Int,
    val text: String? = null,
    val avatar: Int? = null
) : Serializable
