package com.example.hw4.model

import com.google.gson.annotations.SerializedName

class Task {
    var completed: Boolean = false

    @SerializedName
        ("_id")
    var id: String? = null
    var description: String? = null
    var owner: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
}