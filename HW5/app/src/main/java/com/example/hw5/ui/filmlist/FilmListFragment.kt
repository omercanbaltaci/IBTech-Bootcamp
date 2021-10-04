package com.example.hw5.ui.filmlist

import android.app.AlertDialog
import android.os.Handler
import android.os.Looper
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.R
import com.example.hw5.base.BaseFragment
import com.example.hw5.base.BaseRecyclerViewItemClickListener
import com.example.hw5.databinding.FragmentFilmListBinding
import com.example.hw5.ui.filmlist.adapter.FilmListAdapter
import com.example.hw5.ui.filmlist.model.Film
import com.example.hw5.ui.filmlist.viewmodel.FilmListViewModel
import com.example.hw5.util.ConnectionLiveData
import com.example.hw5.util.changeSpanSize

class FilmListFragment : BaseFragment<FilmListViewModel, FragmentFilmListBinding>() {
    private var list = mutableListOf<Film>()            // Container for the responses
    private var incrementalSize = 0
    private var adapter: FilmListAdapter? = null
    private var progressDialog = Film()
    private lateinit var cld: ConnectionLiveData
    override var viewModel: FilmListViewModel? = null

    override fun getLayoutID() = R.layout.fragment_film_list

    override fun observeLiveData() {
        viewModel?.filmsResponse?.observe(this, {
            dataBinding.filmListResponse = it
            dataBinding.executePendingBindings()
            if (list.contains(progressDialog)) list.removeAt(list.size - 1)
            list.addAll(it.getList())
            adapter?.notifyItemRangeChanged(incrementalSize, it.getList().size)
            incrementalSize += it.getList().size
        })
    }

    override fun prepareView() {
        if (shouldCheckInternetConnection()) {
            cld = ConnectionLiveData(requireActivity().application)
            cld.observe(requireActivity(), { isConnected ->
                when (isConnected) {
                    false -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("No Connection")
                            .setMessage("Couldn't connect the internet.")
                            .setCancelable(true)
                            .setNegativeButton("OK", null)
                            .show()
                    }
                }
            })
        }
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.changeSpanSize(list, 2, 1)
        dataBinding.recyclerView.layoutManager = layoutManager
        adapter = FilmListAdapter(list, object : BaseRecyclerViewItemClickListener<Film> {
            override fun onItemClicked(clickedObject: Film, id: Int) {
                when (id) {
                    dataBinding.root.id -> {
                        val bundle = bundleOf("movieId" to clickedObject.id.toString())
                        findNavController().navigate(
                            R.id.action_viewPagerContainerFragment_to_filmDetailsFragment,
                            bundle
                        )
                    }
                }
            }
        })
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && !recyclerView.canScrollVertically(1)
                    && !list.contains(progressDialog)
                ) {
                    progressDialog.viewType = 1
                    list.add(progressDialog)
                    adapter!!.notifyItemChanged(list.size - 1)
                    scrollToLastPosition()

                    /*
                     * I've put a delay mechanism here to avoid progress dialog disappearing immediately
                     * since the API is so fast.
                     */
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModel?.getOtherMovies()
                    }, 1000)

                    //viewModel?.getOtherMovies()
                }
            }
        })
    }

    override fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(FilmListViewModel::class.java)
    }

    private fun scrollToLastPosition() {
        dataBinding.recyclerView.scrollToPosition(list.size - 1)
    }
}