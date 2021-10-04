package com.example.hw5.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hw5.util.PREF_NAME
import com.example.hw5.util.PrefUtils

abstract class BaseFragment<VM : ViewModel?, DB : ViewDataBinding> : Fragment(), FragmentActions,
    FragmentInterface {
    abstract val viewModel: VM?
    protected lateinit var prefUtils: PrefUtils
    protected lateinit var dataBinding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)
        prefUtils = PrefUtils.with(requireActivity(), PREF_NAME, Context.MODE_PRIVATE)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prepareViewModel()
        observeLiveData()
    }

    override fun changeStatusBarColor(id: Int) {
        activity?.window?.statusBarColor = resources.getColor(id)
    }

    abstract fun getLayoutID(): Int
    abstract fun observeLiveData()
    abstract fun prepareView()
    abstract fun prepareViewModel()
    override fun shouldCheckInternetConnection() = true

    // extensiona al
    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = f() as T
        }
}