package com.example.android.nhatrosv.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


class Repository {
    companion object {
        private val apartmentsServiceClient: ApartmentsServiceClient =
            ApartmentsServiceClient.create()
    }

    fun getApartments(): MutableLiveData<List<Apartment>> {
        val data: MutableLiveData<List<Apartment>> = MutableLiveData()
        apartmentsServiceClient.getApartments()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.v("list nhà trọ", "" + result)
                    data.value = result
                },
                { error -> Log.e("ERROR", error.message!!) }
            )
        return data
    }

    fun getComment(idNhaTro: Int): MutableLiveData<List<Comment>> {
        val data: MutableLiveData<List<Comment>> = MutableLiveData()
        apartmentsServiceClient.getComments(idNhaTro)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.v("list comment", "" + result)
                    data.value = result
                    Log.v("list comment", "" + data.value.toString())
                },
                { error ->
                    Log.e("databinhluan", data.value.toString())
                    Log.e("binhluan", error.message!!)
                }

            )
        return data
    }

    fun sendComment(apartment: Apartment, userId: Int, content: String): MutableLiveData<String> {
        val data: MutableLiveData<String> = MutableLiveData()
        val sdf = SimpleDateFormat("dd/MM/yyy", Locale.US)
        val date = sdf.format(Date())
        apartmentsServiceClient.sendComment(apartment.id!!, userId, content, date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    data.value = result
                },
                { error ->
                    Log.e("response sendComment", error.message)
                }
            )
        return data
    }
}