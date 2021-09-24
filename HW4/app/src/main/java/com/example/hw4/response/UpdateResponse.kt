package com.example.hw4.response

import com.example.hw4.model.Task

data class UpdateResponse(
    val success: Boolean,
    val data: Task
)