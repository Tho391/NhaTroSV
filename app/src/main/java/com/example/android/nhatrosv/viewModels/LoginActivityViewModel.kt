package com.example.android.nhatrosv.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.nhatrosv.api.Repository
import com.example.android.nhatrosv.models.Token
import com.example.android.nhatrosv.models.User

class LoginActivityViewModel : ViewModel() {
    fun login(username: String, password: String):MutableLiveData<Token>{
        return Repository().login(username,password)
    }

    fun login(user: User) :MutableLiveData<Token>{
        return Repository().login(user)
    }
}