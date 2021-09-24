package com.example.hw4.service

import com.example.hw4.model.User
import com.example.hw4.response.LoginResponse
import com.example.hw4.response.TaskResponse
import com.example.hw4.response.UpdateResponse
import retrofit2.Call
import retrofit2.http.*

interface TaskerAPI {
    @GET("user/me")
    fun getMe(): Call<User>

    @POST("user/login")
    fun login(@Body params: MutableMap<String, String>): Call<LoginResponse>

    @GET("task")
    fun getAllTasks(): Call<TaskResponse>

    @GET("task")
    fun getTaskByPagination(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Call<TaskResponse>

    @PUT("task/{id}")
    fun updateTask(
        @Path("id") id: String,
        @Body param: MutableMap<String, Boolean>
    ): Call<UpdateResponse>

    @DELETE("task/{id}")
    fun deleteTask(@Path("id") id: String): Call<UpdateResponse>

    @POST("task")
    fun addTask(@Body param: MutableMap<String, String>): Call<UpdateResponse>
}