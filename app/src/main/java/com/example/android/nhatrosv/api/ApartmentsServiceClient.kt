package com.example.android.nhatrosv.api

import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment
import com.example.android.nhatrosv.models.ResponseAddComment
import com.example.android.nhatrosv.models.Token
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
    fun getApartments(@Header("authorization") token: String): Observable<List<Apartment>>

    @FormUrlEncoded
    @POST("api/LayDanhSachBL")
    fun getComments(
        @Header("authorization") token: String,
        @Field("idnhatro") idNhaTro: Int
    ): Observable<List<Comment>>

    @FormUrlEncoded
    @POST("api/ThemBinhLuan")
    fun sendComment(
        @Header("authorization") token: String,
        @Field("idnhatro") idNhaTro: Int,
        @Field("idnguoidung") idNguoiDung: Int,
        @Field("noidung") noiDung: String,
        @Field("date") date: String
    ): Observable<ResponseAddComment>

    @FormUrlEncoded
    @POST("api/getnhatroid")
    fun getApartment(
        @Header("authorization") token: String,
        @Field("idnhatro") idNhaTro: Int
    ): Observable<Apartment>

    @FormUrlEncoded
    @POST("api/loginuser")
    fun login(
        @Field("Username") username: String,
        @Field("Password") password: String
    ): Observable<Token>

    @FormUrlEncoded
    @POST("api/loginuser")
    fun login(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("gmail") email: String,
        @Field("photourl") photoUrl: String
    ): Observable<Token>

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