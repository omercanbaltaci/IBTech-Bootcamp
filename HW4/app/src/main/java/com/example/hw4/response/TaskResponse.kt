package com.example.hw4.response

import com.example.hw4.model.Task

data class TaskResponse(
    val data: MutableList<Task>,
    val count: Int
)