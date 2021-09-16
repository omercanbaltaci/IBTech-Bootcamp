package com.example.hw3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hw3.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {
    override fun isNavigationbarVisible(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showPopUp()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_btn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_avatarFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeStatusBarColor(R.color.battle_background)
    }

    override fun onResume() {
        super.onResume()

        changeStatusBarColor(R.color.battle_background)
    }

    override fun getLayoutID(): Int = R.layout.fragment_profile
}