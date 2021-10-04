package com.example.hw5.ui.filmlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.example.hw5.repository.FilmRepository
import com.example.hw5.ui.filmlist.model.FavoriteFilmsViewStateModel
import com.example.hw5.ui.filmlist.model.FilmDetailsViewStateModel
import com.example.hw5.util.API_KEY

class FilmDetailsViewModel(movieId: String, myApplication: Application) :
    AndroidViewModel(myApplication) {
    val detailsResponse = MediatorLiveData<FilmDetailsViewStateModel>()
    val favoritesResponse = MediatorLiveData<FavoriteFilmsViewStateModel>()
    private val filmRepository = FilmRepository()

    init {
        filmRepository.getDetails(movieId, API_KEY)
        filmRepository.getFavoriteMovies(myApplication)

        detailsResponse.addSource(filmRepository.onFilmDetailsFetched) {
            detailsResponse.value = FilmDetailsViewStateModel(it)
        }

        favoritesResponse.addSource(filmRepository.onFavoriteFilmsFetched) {
            favoritesResponse.value = FavoriteFilmsViewStateModel(it)
        }
    }
}