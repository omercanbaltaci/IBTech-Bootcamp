package com.example.hw3.avatarselection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hw3.R
import com.example.hw3.avatarselection.model.Data

class RecyclerAdapter(private val context: Context, var list: ArrayList<Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // These global variables help selecting and deselecting avatars
    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    // Indicators for view types
    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    // First view, takes the whole first row of the GridLayout
    private inner class View1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.avatar_text)     // Get the textview here
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            message.text = recyclerViewModel.text                           // Change it here
        }
    }

    // Second view, represents the avatars
    private inner class View2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(R.id.avatar_image)    // Get imageview here
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            recyclerViewModel.avatar?.let {
                avatar.setImageResource(it)                                 // If it's not null, then populate it
            }
        }

        /*
        What is meant by checkbox here is the imageview under the avatars which
        gets changed by user's selection
         */
        val checkbox: ImageView = itemView.findViewById(R.id.checkbox_icon)
        fun default() {
            checkbox.setImageResource(0)                        // Meaning, no image source
        }

        fun selected() {
            checkbox.setImageResource(R.drawable.ic_checked)    // Set it checked
        }

        init {
            /*
            Whatever position the adapter gives, selectedposition gets it,
            then we notify the observers about that something has been changed in the logic
             */
            itemView.setOnClickListener {
                selectedItemPos = adapterPosition
                lastItemSelectedPos = if (lastItemSelectedPos == -1)
                    selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }
    }

    // Here we select if the model wants the first view, the text, or the second view, the avatar
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

    /*
    Basic onBind function but what it also does is if selecteditem is really
    eqaul to the position then change it's imageview via selected()
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType == VIEW_TYPE_ONE && holder is View1ViewHolder) {
            holder.bind(position)
        } else {
            (holder as View2ViewHolder).bind(position)

            if (position == selectedItemPos)
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