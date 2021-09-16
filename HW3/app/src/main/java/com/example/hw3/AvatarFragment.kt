package com.example.hw3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.hw3.avatarselection.adapter.RecyclerAdapter
import com.example.hw3.avatarselection.model.Data
import com.example.hw3.base.BaseFragment

class AvatarFragment : BaseFragment() {
    override fun isNavigationbarVisible(): Boolean = false
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The data, basically
        val dataList = ArrayList<Data>()
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_ONE, getString(R.string.choose_avatar)))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar1))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar2))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar3))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar4))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar5))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar6))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar7))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar8))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar9))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar1))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar2))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar3))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar4))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar5))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar6))
        dataList.add(Data(RecyclerAdapter.VIEW_TYPE_TWO, null, R.drawable.ic_avatar7))

        // Get the adapter and the recyclerview here
        val adapter = RecyclerAdapter(requireContext(), dataList)
        recyclerView = activity?.findViewById(R.id.recyclerview) ?: RecyclerView(requireContext())

        // Instantiate the LayoutManager as grid and give it a span count of 3
        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 3
                    else -> 1
                }
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        // Disabling flickering animation below
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeStatusBarColor(R.color.avatar_background)
    }

    override fun getLayoutID(): Int = R.layout.fragment_avatar
}