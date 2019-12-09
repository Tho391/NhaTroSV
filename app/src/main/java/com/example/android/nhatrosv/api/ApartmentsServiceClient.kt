package com.example.android.nhatrosv.api

import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApartmentsServiceClient {

//    @get: GET("/getnhatro")
//    val apartmentList: Observable<ApartmentList>
//
//    @GET("getnhatro")
//    fun getAnswers(): Observable<ApartmentList>

    //    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
//    fun getAnswers(@Query("tagged") tags: String = ""): Observable<ApartmentList>
    @GET("api/getnhatro")
    fun getApartments(): Observable<List<Apartment>>

    @FormUrlEncoded
    @POST("api/LayDanhSachBL")
    fun getComments(@Field("idnhatro") idNhaTro: Int): Observable<List<Comment>>

    @FormUrlEncoded
    @PUT("api/ThemBinhLuan")
    fun sendComment(
        @Field("idnhatro") idNhaTro: Int,
        @Field("idnguoidung") idnguoidung: Int,
        @Field("noidung") noidung: String,
        @Field("date") date: String
    ): Observable<String>

    companion object {
        fun create(): ApartmentsServiceClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://lit-eyrie-49840.herokuapp.com/")
                .build()
            return retrofit.create(ApartmentsServiceClient::class.java)
        }
    }
}