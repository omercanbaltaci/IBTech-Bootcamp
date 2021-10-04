package com.example.hw5.network

import com.example.hw5.network.response.FilmDetailsResponse
import com.example.hw5.network.response.FilmListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmAPI {
    @GET("popular")
    fun getPopularMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int = 1
    ): Call<FilmListResponse>

    @GET("{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apikey: String
    ): Call<FilmDetailsResponse>
}