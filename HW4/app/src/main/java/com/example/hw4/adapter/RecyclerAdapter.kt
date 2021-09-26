package com.example.hw4.adapter

import android.util.Log
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
import ru.rambler.libs.swipe_layout.SwipeLayout

class RecyclerAdapter(var list: MutableList<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: BaseRecyclerViewItemClickListener<Task>? = null

    constructor(
        list: MutableList<Task>,
        itemClickListener: BaseRecyclerViewItemClickListener<Task>
    ) : this(list) {
        this.itemClickListener = itemClickListener
    }

    // First viewtype is for the tasks, the other one if for the progressbar
    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    // Here we select if the model wants the first view, the text, or the second view, the avatar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONE) {
            TaskViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_task, parent, false)
            )
        } else {
            ProgressBarViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_progress, parent, false)
            )
        }
    }

    /*
    Basic onBind function but what it also does is if selecteditem is really
    equal to the position then change it's imageview via selected()
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = this.list[position]
        if (list[position].viewType == VIEW_TYPE_ONE && holder is TaskViewHolder) {
            /*
             * So here, if one of the tasks views are swiped, we check, when we come back to it
             * if its "isSwiped" attribute is true, then we swipe it to right.
             */
            if (!task.isSwiped) holder.swipeLayout.reset()
            else holder.swipeLayout.animateSwipeRight()
            holder.setData(task)
            holder.setOnItemClickListener(task, this.itemClickListener!!)

            holder.swipeLayout.setOnSwipeListener(object : SwipeLayout.OnSwipeListener {
                override fun onBeginSwipe(swipeLayout: SwipeLayout?, moveToRight: Boolean) {
                    task.isSwiped = !task.isSwiped
                }

                override fun onSwipeClampReached(swipeLayout: SwipeLayout?, moveToRight: Boolean) {
                    Log.d("Operation: ", "skipped.")
                }

                override fun onLeftStickyEdge(swipeLayout: SwipeLayout?, moveToRight: Boolean) {
                    Log.d("Operation: ", "skipped.")
                }

                override fun onRightStickyEdge(swipeLayout: SwipeLayout?, moveToRight: Boolean) {
                    Log.d("Operation: ", "skipped.")
                }

            })
        } else {
            this.list[position]
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    override fun getItemCount(): Int = list.size
}

// The view, represents the tasks
class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var completion: ImageView = itemView.findViewById(R.id.row_task_image)
    var description: TextView = itemView.findViewById(R.id.row_task_text)
    var swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe_layout)

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

class ProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)