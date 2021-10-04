package com.example.hw5.ui.filmlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.R
import com.example.hw5.base.BaseRecyclerViewItemClickListener
import com.example.hw5.databinding.RowFilmBinding
import com.example.hw5.ui.filmlist.model.Film

class FilmListAdapter(var filmList: List<Film>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: BaseRecyclerViewItemClickListener<Film>? = null

    constructor(
        filmList: List<Film>,
        itemClickListener: BaseRecyclerViewItemClickListener<Film>
    ) : this(filmList) {
        this.itemClickListener = itemClickListener
    }

    companion object {
        const val VIEW_TYPE_ONE = 0
        const val VIEW_TYPE_TWO = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONE) {
            FilmViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_film,
                    parent,
                    false
                )
            )
        } else {
            ProgressViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.column_progress, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val film = this.filmList[position]
        if (film.viewType == 0) {
            (holder as FilmViewHolder).populate(film)
            holder.setOnItemClickListener(film, this.itemClickListener!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return filmList[position].viewType
    }

    override fun getItemCount() = this.filmList.size
}

class FilmViewHolder(private val binding: RowFilmBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun populate(film: Film) {
        binding.film = film
        binding.executePendingBindings()
    }

    fun setOnItemClickListener(
        film: Film,
        itemClickListener: BaseRecyclerViewItemClickListener<Film>?
    ) {
        val view = binding.root
        view.setOnClickListener {
            itemClickListener!!.onItemClicked(film, it.id)
        }
    }
}

class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)