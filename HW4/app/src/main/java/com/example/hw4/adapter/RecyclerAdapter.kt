package com.example.hw4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hw4.R
import com.example.hw4.base.BaseRecyclerViewItemClickListener
import com.example.hw4.model.Task

class RecyclerAdapter(var list: MutableList<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: BaseRecyclerViewItemClickListener<Task>? = null

    constructor(
        list: MutableList<Task>,
        itemClickListener: BaseRecyclerViewItemClickListener<Task>
    ) : this(list) {
        this.itemClickListener = itemClickListener
    }

    // Here we select if the model wants the first view, the text, or the second view, the avatar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_task, parent, false)
        )
    }

    /*
    Basic onBind function but what it also does is if selecteditem is really
    eqaul to the position then change it's imageview via selected()
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = this.list[position]
        (holder as TaskViewHolder).setData(task)
        holder.setOnItemClickListener(task, this.itemClickListener!!)
    }

    override fun getItemCount(): Int = list.size
}

// The view, represents the tasks
class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var completion: ImageView = itemView.findViewById(R.id.row_task_image)
    var description: TextView = itemView.findViewById(R.id.row_task_text)

    fun setData(task: Task) {
        changeCompletion(task)
        description.text = task.description
    }

    fun setOnItemClickListener(
        task: Task,
        itemClickListener: BaseRecyclerViewItemClickListener<Task>?
    ) {
        val complete = itemView.findViewById<FrameLayout>(R.id.complete_bg)
        complete.setOnClickListener {
            itemClickListener!!.onItemClicked(task, it.id)
            changeCompletion(task)
        }
        val delete = itemView.findViewById<FrameLayout>(R.id.delete_bg)
        delete.setOnClickListener {
            itemClickListener!!.onItemClicked(task, it.id)
        }
    }

    private fun changeCompletion(task: Task) {
        if (task.completed) completion.setImageResource(R.drawable.ic_marked)
        else completion.setImageResource(R.drawable.ic_unmarked)
    }
}