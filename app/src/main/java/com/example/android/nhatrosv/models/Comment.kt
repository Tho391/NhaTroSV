package com.example.android.nhatrosv.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Comment(
    @SerializedName("IdNguoiDung")
    @Expose
    val id: Int?,
    @SerializedName("Ten")
    @Expose
    val firstName: String?,
    @SerializedName("Ho")
    @Expose
    val lastName: String?,
    @SerializedName("photourl")
    @Expose
    val photoUrl: String?,
    @SerializedName("noidung")
    @Expose
    val content: String?,
    @SerializedName("date")
    @Expose
    val date: String?
)