package com.example.android.nhatrosv.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.nhatrosv.api.Repository
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment

class MainActivityViewModel : ViewModel() {

    //private var apartments: MutableLiveData<List<Apartment>> = Repository().getApartments()

    fun getComments(id: Int):MutableLiveData<List<Comment>>{
        return Repository().getComment(id)
    }

    fun getApartments(): MutableLiveData<List<Apartment>> {
        //return apartments
        return Repository().getApartments()
    }

    fun sendComment(apartment: Apartment,userId: Int, content: String):MutableLiveData<String> {
        return Repository().sendComment(apartment,userId,content)
    }

}