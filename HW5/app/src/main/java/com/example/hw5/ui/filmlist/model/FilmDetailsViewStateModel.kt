package com.example.hw5.ui.filmlist.model

import com.example.hw5.network.response.FilmDetailsResponse

data class FilmDetailsViewStateModel(
    val detailsResponse: FilmDetailsResponse,
) {
    fun getFilmTitle(): String = detailsResponse.title
    fun getFilmOverview(): String = detailsResponse.overview
    fun getId(): String = detailsResponse.id.toString()
    fun getYear(): String = detailsResponse.release_date.substring(0, 4)
    fun getDuration(): String {
        val hours = detailsResponse.runtime / 60
        val remainder = detailsResponse.runtime - (hours * 60)
        return if (remainder != 0) hours.toString() + "h " + remainder.toString() + "min"
        else hours.toString() + "h"
    }

    fun getAverageScore() = detailsResponse.vote_average
    fun getTotalVoteCount() = detailsResponse.vote_count
    fun getPosterPath(): String = detailsResponse.poster_path
    fun getGenres(): List<Genre> = detailsResponse.genres
}