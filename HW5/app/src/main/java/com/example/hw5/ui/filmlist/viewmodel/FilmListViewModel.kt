package com.example.hw5.ui.filmlist.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.hw5.repository.FilmRepository
import com.example.hw5.ui.filmlist.model.FilmListViewStateModel
import com.example.hw5.util.API_KEY

class FilmListViewModel : ViewModel() {
    val filmsResponse = MediatorLiveData<FilmListViewStateModel>()
    private val filmRepository = FilmRepository()
    var pageNo = 1

    init {
        filmRepository.getMovieList(API_KEY, 1)

        filmsResponse.addSource(filmRepository.onFilmsFetched) {
            filmsResponse.value = FilmListViewStateModel(it)
        }
    }

    // Getting other movies to populate the RV here.
    fun getOtherMovies() {
        filmRepository.getMovieList(API_KEY, ++pageNo)
    }
}