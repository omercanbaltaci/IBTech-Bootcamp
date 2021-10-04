package com.example.hw5.ui.filmlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.R
import com.example.hw5.databinding.ColumnGenreBinding
import com.example.hw5.ui.filmlist.model.Genre

class GenreListAdapter(private val genreList: List<Genre>) :
    RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.column_genre,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = this.genreList[position]
        holder.populate(genre)
    }

    override fun getItemCount() = this.genreList.size
}

class GenreViewHolder(private val binding: ColumnGenreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun populate(genre: Genre) {
        binding.genre = genre
        binding.executePendingBindings()
    }
}