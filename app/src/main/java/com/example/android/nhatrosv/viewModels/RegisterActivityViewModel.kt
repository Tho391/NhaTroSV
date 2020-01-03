package com.example.android.nhatrosv.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.nhatrosv.api.Repository
import com.example.android.nhatrosv.models.Response

class RegisterActivityViewModel : ViewModel() {
    fun register(
        username: String,
        password: String,
        ho: String,
        ten: String,
        ngaySinh: String,
        diaChi: String,
        sdt: String,
        photourl: String
    ): MutableLiveData<Response> {
        return Repository().register(username, password, ho, ten, ngaySinh, diaChi, sdt, photourl)
    }
}