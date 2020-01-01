package com.example.android.nhatrosv.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.nhatrosv.api.Repository
import com.example.android.nhatrosv.models.Response
import com.example.android.nhatrosv.models.User

class LoginActivityViewModel : ViewModel() {
    fun login(username: String, password: String):MutableLiveData<Response>{
        return Repository().login(username,password)
    }

    fun login(user: User) :MutableLiveData<Response>{
        return Repository().login(user)
    }

    fun getInfo(token: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}