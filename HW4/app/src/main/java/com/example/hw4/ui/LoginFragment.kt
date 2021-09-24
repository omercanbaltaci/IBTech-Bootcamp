package com.example.hw4.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.navigation.fragment.findNavController
import com.example.hw3.base.BaseFragment
import com.example.hw4.R
import com.example.hw4.response.LoginResponse
import com.example.hw4.service.BaseCallBack
import com.example.hw4.service.ServiceConnector
import com.example.hw4.utils.USER_TOKEN
import com.example.hw4.utils.getString
import com.example.hw4.utils.saveDataAsString
import com.example.hw4.utils.toast

class LoginFragment : BaseFragment() {
    override fun getLayoutID(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareView()
    }

    override fun prepareView() {
        super.prepareView()

        val loginTextButton = activity?.findViewById<AppCompatButton>(R.id.login_text_button)
        loginTextButton?.setOnClickListener {
            hitLoginEndpoint()
        }
    }

    private fun hitLoginEndpoint() {
        val email = activity?.findViewById<AppCompatEditText>(R.id.edittext_email)?.getString()
        val password =
            activity?.findViewById<AppCompatEditText>(R.id.edittext_password)?.getString()

        if (allFieldsAreValid(email!!, password!!)) {
            val params = mutableMapOf<String, String>().apply {
                put("email", email)
                put("password", password)
            }

            ServiceConnector.restInterface.login(params)
                .enqueue(object : BaseCallBack<LoginResponse>() {
                    override fun onSuccess(loginResponse: LoginResponse) {
                        super.onSuccess(loginResponse)

                        saveDataAsString(USER_TOKEN, loginResponse.token)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }

                    override fun onFailure() {
                        super.onFailure()

                        toast(getString(R.string.login_error))
                    }
                })
        } else {
            toast(getString(R.string.please_fill))
        }
    }

    private fun allFieldsAreValid(email: String, password: String): Boolean {
        var allFieldsAreValid = true

        if (email.isEmpty() || !isValidEmail(email)) allFieldsAreValid = false
        if (password.isEmpty()) allFieldsAreValid = false

        return allFieldsAreValid
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}