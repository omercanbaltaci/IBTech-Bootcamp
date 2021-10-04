package com.example.hw5.ui.filmlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.example.hw5.repository.FilmRepository
import com.example.hw5.ui.filmlist.model.FavoriteFilmsViewStateModel

class FavoriteFilmsViewModel(myApplication: Application) : AndroidViewModel(myApplication) {
    val favoriteFilmsResponse = MediatorLiveData<FavoriteFilmsViewStateModel>()
    private val filmRepository = FilmRepository()

    init {
        filmRepository.getFavoriteMovies(myApplication)

        favoriteFilmsResponse.addSource(filmRepository.onFavoriteFilmsFetched) {
            favoriteFilmsResponse.value = FavoriteFilmsViewStateModel(it)
        }
    }

    fun getFavoritesAgain() {
        filmRepository.getFavoriteMovies(this.getApplication())
    }
}