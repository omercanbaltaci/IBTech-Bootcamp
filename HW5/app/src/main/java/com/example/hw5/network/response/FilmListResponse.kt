package com.example.hw5.network.response

import com.example.hw5.ui.filmlist.model.Film
import com.google.gson.annotations.SerializedName

data class FilmListResponse(
    val page: Int,
    @SerializedName("results") val films: List<Film>
)