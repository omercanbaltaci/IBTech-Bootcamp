package com.example.hw4.service

interface BaseResponseHandlerInterface<T> {
    fun onSuccess(data: T)
    fun onFailure()
}