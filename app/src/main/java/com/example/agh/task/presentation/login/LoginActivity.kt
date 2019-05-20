package com.example.agh.task.presentation.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.agh.task.R
import com.example.agh.task.presentation.Utils.afterTextChanged
import com.example.agh.task.presentation.Utils.toast
import com.example.agh.task.presentation.location.LocationActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email.afterTextChanged { viewModel.username.postValue(it) }
        password.afterTextChanged {viewModel.password.postValue(it)  }

        email_sign_in_button.setOnClickListener { viewModel.attemptLogin() }

        viewModel.response.observe(this , Observer {
            when(it){
                0 -> "please fill all fields".toast(this@LoginActivity)
                1 -> startActivity(Intent(this@LoginActivity , LocationActivity::class.java))
            }
        })

    }

}
