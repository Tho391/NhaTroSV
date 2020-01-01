package com.example.android.nhatrosv.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.android.nhatrosv.models.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


class Repository {
    companion object {
        private val apartmentsServiceClient: ApartmentsServiceClient =
            ApartmentsServiceClient.create()
    }

    fun getApartments(token: String): MutableLiveData<List<Apartment>> {
        val data: MutableLiveData<List<Apartment>> = MutableLiveData()
        apartmentsServiceClient.getApartments(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.v("list nhà trọ", "" + result)
                    data.value = result
                },
                { error ->
                    Log.e("ERROR", error.message!!)
                }
            )
        return data
    }

    fun getComment(token: String, idNhaTro: Int): MutableLiveData<List<Comment>> {
        val data: MutableLiveData<List<Comment>> = MutableLiveData()
        apartmentsServiceClient.getComments(token, idNhaTro)
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

    fun sendComment(
        token: String,
        apartmentId: Int,
        userId: Int,
        content: String
    ): MutableLiveData<ResponseAddComment> {
        val data: MutableLiveData<ResponseAddComment> = MutableLiveData()
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val date = sdf.format(Date())
        apartmentsServiceClient.sendComment(token, apartmentId, userId, content, date)
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

    fun getApartment(token: String, id: Int): MutableLiveData<Apartment> {
        val data: MutableLiveData<Apartment> = MutableLiveData()
        apartmentsServiceClient.getApartment(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.v("nhà trọ theo id", "" + result)
                    data.value = result
                },
                { error ->
                    Log.e("id lỗi", error.message)
                }
            )
        return data
    }

    fun login(username: String, password: String): MutableLiveData<Response> {
        val data: MutableLiveData<Response> = MutableLiveData()
        apartmentsServiceClient.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    data.value = result
                },
                { error ->
                    Log.e("lỗi", error.message)
                }
            )
        return data
    }

    fun login(user: User): MutableLiveData<Response> {
        val data: MutableLiveData<Response> = MutableLiveData()
        val array = user.name!!.split(" ")
        val firstName = array[0]
        val lastName = user.name!!.drop(firstName.length + 1)
        apartmentsServiceClient.login(
            user.id!!,
            user.idToken!!,
            lastName,
            firstName,
            user.email!!,
            user.photoUrl!!
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    data.value = result
                },
                { error ->
                    Log.e("lỗi", error.message)
                }
            )
        return data
    }

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
        val data: MutableLiveData<Response> = MutableLiveData()
        apartmentsServiceClient.register(
            username,
            password,
            ho,
            ten,
            ngaySinh,
            diaChi,
            sdt,
            photourl
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    data.value = result
                },
                { error ->
                    Log.e("lỗi", error.message)
                }
            )

        return data
    }
}