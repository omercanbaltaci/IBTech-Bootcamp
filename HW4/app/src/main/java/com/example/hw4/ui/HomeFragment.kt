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
import com.example.hw4.R
import com.example.hw4.adapter.RecyclerAdapter
import com.example.hw4.base.BaseFragment
import com.example.hw4.base.BaseRecyclerViewItemClickListener
import com.example.hw4.model.Task
import com.example.hw4.response.TaskResponse
import com.example.hw4.response.UpdateResponse
import com.example.hw4.service.BaseCallBack
import com.example.hw4.service.ServiceConnector
import com.example.hw4.utils.COMPLETED_BODY
import com.example.hw4.utils.gone
import com.example.hw4.utils.toast
import com.example.hw4.utils.visible

class HomeFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private var pageCount = 0           // Initial number of pages
    private var pageNo = 1              // We populate the RV at first, hence we are at page 1.
    private val LIMIT = 5
    private var SKIP = 5
    private val dataList = mutableListOf<Task>()
    private val progressTask = Task()   // Initializing a ProgressBarView here
    private var fetchedBefore = false   // We haven't fetched any tasks yet.
    private var initialTaskCount = 0
    private var totalTaskCount = 0
    private var refreshList = false
    private val tempList = mutableListOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColor(R.color.white)

        val adapter = RecyclerAdapter(
            dataList,
            object : BaseRecyclerViewItemClickListener<Task> {
                override fun onItemClicked(clickedObject: Task, id: Int) {
                    when (id) {
                        R.id.complete_bg -> {
                            // All this when block does is changing the ImageSource of the checkbox.
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

                            // We are filling in a map to send over with the request
                            val param = mutableMapOf<String, Boolean>().apply {
                                put(COMPLETED_BODY, flag)
                            }
                            ServiceConnector.restInterface.updateTask(clickedObject.id!!, param)
                                .enqueue(object : BaseCallBack<UpdateResponse>() {
                                    override fun onSuccess(updateResponse: UpdateResponse) {
                                        super.onSuccess(updateResponse)
                                        toast(getString(R.string.completed_successfully), 100)
                                    }

                                    override fun onFailure() {
                                        super.onFailure()
                                        toast(getString(R.string.failed))
                                    }
                                })
                        }
                        R.id.delete_bg -> {
                            totalTaskCount--
                            if (totalTaskCount + LIMIT == pageCount * LIMIT) {
                                // If we get into this if condition, meaning the page number is decreased by 1
                                pageCount--
                                pageNo--
                                SKIP -= 5
                            } else SKIP--
                            val clickedIndex = dataList.indexOf(clickedObject)
                            dataList.removeAt(clickedIndex)
                            recyclerView.adapter?.notifyItemRemoved(clickedIndex)
                            ServiceConnector.restInterface.deleteTask(clickedObject.id!!)
                                .enqueue(object : BaseCallBack<UpdateResponse>() {
                                    override fun onSuccess(updateResponse: UpdateResponse) {
                                        super.onSuccess(updateResponse)
                                        if (dataList.size == 0) {
                                            activity?.findViewById<RecyclerView>(R.id.home_rv)
                                                ?.gone()
                                            activity?.findViewById<TextView>(R.id.home_tv)
                                                ?.visible()
                                        }

                                        toast(getString(R.string.completed_successfully), 100)
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

        setPageCount()                  // Setting how many pages we need here.
        getMyTasks(0, adapter)      // Populate the RV with tasks after the view is created.

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

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Blocking the RV from triggering top scroll.
                if (dy <= 0) refreshList = true
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && (didPageCountChange(totalTaskCount) || pageNo < pageCount)
                    && !recyclerView.canScrollVertically(1)
                    && refreshList
                    && !dataList.contains(progressTask)
                ) {
                    fetchedBefore = true            // Make sure that we scroll the RV at least once
                    progressTask.viewType = 2       // Insert the progressview
                    dataList.add(progressTask)
                    scrollToLast()
                    recyclerView.adapter?.notifyItemInserted(dataList.size - 1)
                    getMyTasks(SKIP, adapter)
                    SKIP += LIMIT
                    pageNo++
                } else if (initialTaskCount != totalTaskCount) {
                    // If the tasks we added are within the page range they'll be added automatically.
                    dataList.addAll(tempList)
                    for (i in dataList.size - tempList.size until dataList.size - 1) {
                        recyclerView.adapter?.notifyItemInserted(i)
                    }
                    tempList.clear()
                    initialTaskCount = totalTaskCount
                } else if (pageNo == pageCount) {
                    toast(getString(R.string.no_more_tasks), 20)
                }
            }
        })

        activity?.findViewById<CardView>(R.id.floatingbtn)?.setOnClickListener {
            MaterialDialog(requireContext()).show {
                val dialog = input { dialog, _ ->
                    val param = mutableMapOf<String, String>().apply {
                        put("description", dialog.getInputField().text.toString())
                    }
                    ServiceConnector.restInterface.addTask(param)
                        .enqueue(object : BaseCallBack<UpdateResponse>() {
                            override fun onSuccess(updateResponse: UpdateResponse) {
                                super.onSuccess(updateResponse)
                                totalTaskCount++
                                if (!didPageCountChange(totalTaskCount))
                                    tempList.add(updateResponse.data)

                                refreshList = true
                                activity?.findViewById<RecyclerView>(R.id.home_rv)?.visible()
                                activity?.findViewById<TextView>(R.id.home_tv)?.gone()
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
                    /*
                     * Removing the ProgressBar view from the RV.
                     * If we fetch any tasks, anytime we come down here this if condition will
                     * remove the item at the last position, which is the ProgressBar view.
                     */
                    if (fetchedBefore) {
                        dataList.removeAt(dataList.size - 1)
                        recyclerView.adapter?.notifyItemRemoved(dataList.size - 1)
                    }

                    if (taskResponse.count == 0) {
                        activity?.findViewById<RecyclerView>(R.id.home_rv)?.gone()
                        activity?.findViewById<TextView>(R.id.home_tv)?.visible()
                    } else {
                        if (tempList.isNotEmpty()) {
                            tempList.addAll(taskResponse.data)
                            dataList.addAll(tempList)
                            tempList.clear()
                        } else dataList.addAll(taskResponse.data)
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

    /*
     * All below function does is designating a page number for paging functionality.
     * We need this number to block the user from further reloading new tasks.
     */
    private fun setPageCount() {
        ServiceConnector.restInterface.getAllTasks().enqueue(object : BaseCallBack<TaskResponse>() {
            override fun onSuccess(taskResponse: TaskResponse) {
                super.onSuccess(taskResponse)
                /*
                 * Let's say there is 36 tasks, if SKIP is 5, page count will be 6.
                 * If it's 15, it will be 3.
                 */
                totalTaskCount = taskResponse.count
                initialTaskCount = taskResponse.count
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

    fun didPageCountChange(taskCount: Int): Boolean {
        return if (pageCount * LIMIT < taskCount) {
            pageCount++
            true
        } else false
    }

    // Self-explanatory function. This will force the RecyclerView to scroll to last item's position.
    fun scrollToLast() {
        recyclerView.scrollToPosition(dataList.size - 1)
    }

    override fun getLayoutID(): Int = R.layout.fragment_home
}