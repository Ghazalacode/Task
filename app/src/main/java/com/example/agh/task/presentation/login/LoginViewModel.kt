package com.example.agh.task.presentation.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log


class LoginViewModel : ViewModel() {

    val response= MutableLiveData<Int>()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()



    fun attemptLogin() {
        Log.d("", "attemptLogin() called with: username = [$username], password = [$password]")

   if (username.value.isNullOrBlank() || password.value.isNullOrBlank()) {
            response.postValue(0)
        } else {
            response.postValue(1)
        }
        // other validation logic

    }

}