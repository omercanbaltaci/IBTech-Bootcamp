package com.example.hw5.ui.filmlist

import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.ViewModelProvider
import com.example.hw5.R
import com.example.hw5.base.BaseFragment
import com.example.hw5.databinding.FragmentFilmDetailsBinding
import com.example.hw5.ui.filmlist.adapter.GenreListAdapter
import com.example.hw5.ui.filmlist.viewmodel.FilmDetailsViewModel

class FilmDetailsFragment : BaseFragment<FilmDetailsViewModel, FragmentFilmDetailsBinding>() {
    private var filmTitle: String? = null
    override var viewModel: FilmDetailsViewModel? = null

    override fun getLayoutID(): Int = R.layout.fragment_film_details

    override fun observeLiveData() {
        viewModel?.detailsResponse?.observe(this, {
            dataBinding.detailsResponse = it
            dataBinding.executePendingBindings()
            filmTitle = it.getFilmTitle()
            dataBinding.genreRV.adapter = GenreListAdapter(it.getGenres())
        })
        viewModel?.favoritesResponse?.observe(this, {
            dataBinding.favoritesResponse = it
            dataBinding.executePendingBindings()
        })
    }

    override fun prepareView() {
        dataBinding.favIcon.setOnClickListener {
            if (prefUtils.getAll().containsKey(arguments?.getString("movieId")!!)) {
                prefUtils.removeByKey(arguments?.getString("movieId")!!)
                dataBinding.favIcon.setImageResource(R.drawable.ic_fav_unselected)
            } else {
                prefUtils.save(arguments?.getString("movieId")!!, filmTitle!!)
                dataBinding.favIcon.setImageResource(R.drawable.ic_fav_selected)
            }
        }

        dataBinding.plotSummary.movementMethod = ScrollingMovementMethod()
    }

    override fun prepareViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory {
                FilmDetailsViewModel(
                    arguments?.getString("movieId")!!,
                    requireActivity().application
                )
            }
        ).get(FilmDetailsViewModel::class.java)
    }
}