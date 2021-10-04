package com.example.hw5.ui.filmlist.model

import com.example.hw5.network.response.FilmListResponse

data class FilmListViewStateModel(val filmsResponse: FilmListResponse) {
    fun getList(): List<Film> = filmsResponse.films
}