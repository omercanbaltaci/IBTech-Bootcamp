package com.example.hw4.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.hw3.base.BaseFragment
import com.example.hw4.R
import com.example.hw4.adapter.RecyclerAdapter
import com.example.hw4.base.BaseRecyclerViewItemClickListener
import com.example.hw4.model.Task
import com.example.hw4.response.TaskResponse
import com.example.hw4.response.UpdateResponse
import com.example.hw4.service.BaseCallBack
import com.example.hw4.service.ServiceConnector
import com.example.hw4.utils.gone
import com.example.hw4.utils.toast
import com.example.hw4.utils.visible


class HomeFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private var pageCount = 0
    private var pageNo = 1
    private var limitedTaskCount = 0
    private val LIMIT = 5
    private var SKIP = 5
    private val dataList = mutableListOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecyclerAdapter(
            dataList,
            object : BaseRecyclerViewItemClickListener<Task> {
                override fun onItemClicked(clickedObject: Task, id: Int) {
                    when (id) {
                        R.id.complete_bg -> {
                            val flag: Boolean
                            when {
                                clickedObject.completed -> {
                                    clickedObject.completed = false
                                    flag = false
                                }
                                else -> {
                                    clickedObject.completed = true
                                    flag = true
                                }
                            }

                            val param = mutableMapOf<String, Boolean>().apply {
                                put("completed", flag)
                            }
                            ServiceConnector.restInterface.updateTask(clickedObject.id!!, param)
                                .enqueue(object : BaseCallBack<UpdateResponse>() {
                                    override fun onSuccess(updateResponse: UpdateResponse) {
                                        super.onSuccess(updateResponse)
                                        toast(getString(R.string.completed_successfully), 200)
                                    }

                                    override fun onFailure() {
                                        super.onFailure()
                                        toast(getString(R.string.failed))
                                    }
                                })
                        }
                        R.id.delete_bg -> {
                            val clickedIndex = dataList.indexOf(clickedObject)
                            dataList.removeAt(clickedIndex)
                            recyclerView.adapter?.notifyItemRemoved(clickedIndex)
                            ServiceConnector.restInterface.deleteTask(clickedObject.id!!)
                                .enqueue(object : BaseCallBack<UpdateResponse>() {
                                    override fun onSuccess(updateResponse: UpdateResponse) {
                                        super.onSuccess(updateResponse)
                                        toast(getString(R.string.completed_successfully), 200)
                                    }

                                    override fun onFailure() {
                                        super.onFailure()
                                        toast(getString(R.string.failed))
                                    }
                                })
                        }
                    }
                }
            })

        setPageCount()
        getMyTasks(0, adapter)

        when (pageCount) {
            0 -> toast(getString(R.string.wait_for_tasks), Toast.LENGTH_SHORT)
        }

        /*
        Below is the logic of how the recyclerview works, it'll try to fetch more tasks
        if the it is scrolled.
         */
        recyclerView =
            activity?.findViewById(R.id.home_rv) ?: RecyclerView(requireContext())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && pageNo < pageCount
                    && !recyclerView.canScrollVertically(1)
                ) {
                    getMyTasks(
                        SKIP, adapter
                    )
                    SKIP += LIMIT
                    pageNo++
                } else if (pageNo == pageCount) toast(
                    getString(R.string.no_more_tasks),
                    500
                )
            }
        })

        activity?.findViewById<CardView>(R.id.floatingbtn)?.setOnClickListener {
            MaterialDialog(requireContext()).show {
                val dialog = input { dialog, text ->
                    val param = mutableMapOf<String, String>().apply {
                        put("description", dialog.getInputField().text.toString())
                    }
                    ServiceConnector.restInterface.addTask(param)
                        .enqueue(object : BaseCallBack<UpdateResponse>() {
                            override fun onSuccess(updateResponse: UpdateResponse) {
                                super.onSuccess(updateResponse)
                                dataList.add(updateResponse.data)
                                recyclerView.adapter?.notifyItemInserted(dataList.size - 1)
                                scrollToLast()
                                toast(getString(R.string.completed_successfully))
                            }

                            override fun onFailure() {
                                super.onFailure()
                                toast(getString(R.string.failed))
                            }
                        })
                }
                positiveButton(R.string.add_task_material)
                negativeButton(R.string.cancel)
            }
        }
    }

    private fun getMyTasks(skip: Int, adapter: RecyclerAdapter) {
        ServiceConnector.restInterface.getTaskByPagination(LIMIT, skip)
            .enqueue(object : BaseCallBack<TaskResponse>() {
                override fun onSuccess(taskResponse: TaskResponse) {
                    super.onSuccess(taskResponse)
                    limitedTaskCount = taskResponse.count
                    if (taskResponse.count == 0) {
                        activity?.findViewById<RecyclerView>(R.id.home_rv)?.gone()
                        activity?.findViewById<TextView>(R.id.home_tv)?.visible()
                    } else {
                        dataList.addAll(taskResponse.data)
                        scrollToLast()
                        recyclerView.adapter = adapter
                    }
                }

                override fun onFailure() {
                    super.onFailure()
                    toast(getString(R.string.login_error))
                }
            })
    }

    private fun setPageCount() {
        ServiceConnector.restInterface.getAllTasks().enqueue(object : BaseCallBack<TaskResponse>() {
            override fun onSuccess(taskResponse: TaskResponse) {
                super.onSuccess(taskResponse)
                val quotient = taskResponse.count / SKIP
                pageCount = if (taskResponse.count == quotient * SKIP) quotient
                else quotient + 1
            }

            override fun onFailure() {
                super.onFailure()
                toast(getString(R.string.login_error))
            }
        })
    }

    fun scrollToLast() {
        recyclerView.scrollToPosition(dataList.size - 1)
    }

    override fun getLayoutID(): Int = R.layout.fragment_home
}