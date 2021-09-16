package com.example.hw3.avatarselection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hw3.avatarselection.model.Data
import com.example.hw3.R

class RecyclerAdapter(private val context: Context, var list: ArrayList<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private inner class View1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.avatar_text)
        fun bind (position: Int) {
            val recyclerViewModel = list[position]
            message.text = recyclerViewModel.text
            print(recyclerViewModel.text)
        }
    }

    private inner class View2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(R.id.avatar_image)
        fun bind (position: Int) {
            val recyclerViewModel = list[position]
            recyclerViewModel.avatar?.let {
                avatar.setImageResource(it)
            }
        }

        val checkbox: ImageView = itemView.findViewById(R.id.checkbox_icon)
        fun default() {
            checkbox.setImageResource(0)
        }

        fun selected() {
            checkbox.setImageResource(R.drawable.ic_checked)
        }

        init {
            itemView.setOnClickListener {
                selectedItemPos = adapterPosition
                if(lastItemSelectedPos == -1)
                    lastItemSelectedPos = selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONE) {
            View1ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.row_text, parent, false)
            )
        } else {
            View2ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.row_avatar, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType == VIEW_TYPE_ONE && holder is View1ViewHolder) {
            holder.bind(position)
        } else {
            (holder as View2ViewHolder).bind(position)

            if(position == selectedItemPos)
                holder.selected()
            else
                holder.default()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    override fun getItemCount(): Int = list.size
}