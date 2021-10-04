package com.example.hw5.network.response

import com.example.hw5.ui.filmlist.model.Genre

data class FilmDetailsResponse(
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val id: Int,
    val vote_count: Int,
    val title: String,
    val runtime: Int,
    val overview: String,
    val genres: List<Genre>
)