package com.example.android.nhatrosv.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.nhatrosv.api.Repository
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment
import com.example.android.nhatrosv.models.ResponseAddComment

class MainActivityViewModel : ViewModel() {

    //private var apartments: MutableLiveData<List<Apartment>> = Repository().getApartments()

    fun getComments(token: String,id: Int):MutableLiveData<List<Comment>>{
        return Repository().getComment(token,id)
    }

    fun getApartments(token: String): MutableLiveData<List<Apartment>> {
        //return apartments
        return Repository().getApartments(token)
    }
    fun getApartment(token: String, id:Int):MutableLiveData<Apartment>{
        return Repository().getApartment(token,id)
    }

    fun sendComment(token: String,apartmentId: Int, userId: Int, content: String):MutableLiveData<ResponseAddComment> {
        return Repository().sendComment(token,apartmentId,userId,content)
    }

}