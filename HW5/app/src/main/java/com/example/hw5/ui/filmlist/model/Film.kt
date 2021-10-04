package com.example.hw5.ui.filmlist.model

data class Film(
    val poster_path: String? = null,
    val title: String? = null,
    val vote_average: Double? = null,
    val id: Int? = null,
    var viewType: Int = 0
)