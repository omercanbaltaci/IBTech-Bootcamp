package com.example.hw5.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.hw5.network.BaseCallBack
import com.example.hw5.network.ServiceConnector
import com.example.hw5.network.response.FavoriteFilmsResponse
import com.example.hw5.network.response.FilmDetailsResponse
import com.example.hw5.network.response.FilmListResponse
import com.example.hw5.util.PREF_NAME
import com.example.hw5.util.PrefUtils

class FilmRepository {
    val onFilmsFetched = MutableLiveData<FilmListResponse>()
    val onFilmDetailsFetched = MutableLiveData<FilmDetailsResponse>()
    val onFavoriteFilmsFetched = MutableLiveData<FavoriteFilmsResponse>()

    fun getMovieList(apikey: String, page: Int) {
        ServiceConnector.restInterface.getPopularMovies(apikey, page)
            .enqueue(object : BaseCallBack<FilmListResponse>() {
                override fun onSuccess(data: FilmListResponse) {
                    onFilmsFetched.value = data
                }
            })
    }

    fun getDetails(movieId: String, apikey: String) {
        ServiceConnector.restInterface.getMovieDetails(movieId, apikey)
            .enqueue(object : BaseCallBack<FilmDetailsResponse>() {
                override fun onSuccess(data: FilmDetailsResponse) {
                    onFilmDetailsFetched.value = data
                }
            })
    }

    fun getFavoriteMovies(context: Context) {
        val prefUtils = PrefUtils.with(context, PREF_NAME, Context.MODE_PRIVATE)
        val data = FavoriteFilmsResponse(prefUtils.getAll())
        onFavoriteFilmsFetched.value = data
    }
}