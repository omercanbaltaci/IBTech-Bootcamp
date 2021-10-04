package com.example.hw5.ui.filmlist.model

import com.example.hw5.network.response.FavoriteFilmsResponse

class FavoriteFilmsViewStateModel(private val favoriteFilmsResponse: FavoriteFilmsResponse) {
    fun getFavoriteFilms(): MutableList<FavoriteFilm> {
        val favoriteFilmList = mutableListOf<FavoriteFilm>()
        favoriteFilmsResponse.favoriteFilms.forEach {
            val myFavoriteFilm = FavoriteFilm(it.key, it.value as String)
            favoriteFilmList.add(myFavoriteFilm)
        }
        return favoriteFilmList
    }

    fun getFavoritedIds(): MutableList<String> {
        val favoritedIdList = mutableListOf<String>()
        favoriteFilmsResponse.favoriteFilms.forEach {
            favoritedIdList.add(it.key)
        }
        return favoritedIdList
    }
}