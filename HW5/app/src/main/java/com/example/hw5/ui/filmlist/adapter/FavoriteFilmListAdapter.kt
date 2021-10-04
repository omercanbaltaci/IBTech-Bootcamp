package com.example.hw5.ui.filmlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.R
import com.example.hw5.base.BaseRecyclerViewItemClickListener
import com.example.hw5.databinding.RowFavoriteFilmBinding
import com.example.hw5.ui.filmlist.model.FavoriteFilm

class FavoriteFilmListAdapter(var favoriteFilmList: List<FavoriteFilm>) :
    RecyclerView.Adapter<FavoriteFilmViewHolder>() {
    private var itemClickListener: BaseRecyclerViewItemClickListener<FavoriteFilm>? = null

    constructor(
        favoriteFilmList: List<FavoriteFilm>,
        itemClickListener: BaseRecyclerViewItemClickListener<FavoriteFilm>
    ) : this(favoriteFilmList) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFilmViewHolder {
        return FavoriteFilmViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_favorite_film,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteFilmViewHolder, position: Int) {
        val favoriteFilm = this.favoriteFilmList[position]
        holder.populate(favoriteFilm)
        holder.setOnItemClickListener(favoriteFilm, this.itemClickListener)
    }

    override fun getItemCount() = this.favoriteFilmList.size
}

class FavoriteFilmViewHolder(private val binding: RowFavoriteFilmBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun populate(favoriteFilm: FavoriteFilm) {
        binding.favoriteFilm = favoriteFilm
        binding.executePendingBindings()
    }

    fun setOnItemClickListener(
        favoriteFilm: FavoriteFilm,
        itemClickListener: BaseRecyclerViewItemClickListener<FavoriteFilm>?
    ) {
        binding.deleteButton.setOnClickListener {
            itemClickListener!!.onItemClicked(favoriteFilm, it.id)
        }
        binding.gotomovie.setOnClickListener {
            itemClickListener!!.onItemClicked(favoriteFilm, it.id)
        }
    }
}