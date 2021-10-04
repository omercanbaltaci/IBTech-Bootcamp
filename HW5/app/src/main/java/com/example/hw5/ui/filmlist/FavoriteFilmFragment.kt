package com.example.hw5.ui.filmlist

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hw5.R
import com.example.hw5.base.BaseFragment
import com.example.hw5.base.BaseRecyclerViewItemClickListener
import com.example.hw5.databinding.FragmentFavoriteFilmBinding
import com.example.hw5.ui.filmlist.adapter.FavoriteFilmListAdapter
import com.example.hw5.ui.filmlist.model.FavoriteFilm
import com.example.hw5.ui.filmlist.viewmodel.FavoriteFilmsViewModel
import com.example.hw5.util.gone
import com.example.hw5.util.visible

class FavoriteFilmFragment : BaseFragment<FavoriteFilmsViewModel, FragmentFavoriteFilmBinding>() {
    private var mList = mutableListOf<FavoriteFilm>()
    override var viewModel: FavoriteFilmsViewModel? = null

    override fun getLayoutID(): Int = R.layout.fragment_favorite_film

    override fun observeLiveData() {
        viewModel?.favoriteFilmsResponse?.observe(this, {
            dataBinding.favoriteFilmsResponse = it
            dataBinding.executePendingBindings()
            mList = it.getFavoriteFilms()
            checkVisibilities()
            dataBinding.favoriteFilmRV.adapter = FavoriteFilmListAdapter(
                mList,
                object : BaseRecyclerViewItemClickListener<FavoriteFilm> {
                    override fun onItemClicked(clickedObject: FavoriteFilm, id: Int) {
                        when (id) {
                            R.id.deleteButton -> {
                                val clickedIndex = mList.indexOf(clickedObject)
                                mList.removeAt(clickedIndex)
                                dataBinding.favoriteFilmRV.adapter?.notifyItemRemoved(clickedIndex)
                                checkVisibilities()
                                prefUtils.removeByKey(clickedObject.id!!)
                            }
                            R.id.gotomovie -> {
                                val bundle = bundleOf("movieId" to clickedObject.id.toString())
                                findNavController().navigate(
                                    R.id.action_viewPagerContainerFragment_to_filmDetailsFragment,
                                    bundle
                                )
                            }
                        }
                    }
                })
        })
    }

    override fun prepareView() {
        viewModel?.getFavoritesAgain()
    }

    override fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteFilmsViewModel::class.java)
    }

    private fun checkVisibilities() {
        if (mList.size == 0) {
            dataBinding.notify.visible()
            dataBinding.favoriteFilmRV.gone()
        } else {
            dataBinding.notify.gone()
            dataBinding.favoriteFilmRV.visible()
        }
    }
}