package com.example.android.nhatrosv.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Comment(
    @SerializedName("IdNguoiDung")
    @Expose
    val id: Int?,
    @SerializedName("Ho")
    @Expose
    val firstName: String?,
    @SerializedName("Ten")
    @Expose
    val lastName: String?,
    @SerializedName("photourl")
    @Expose
    val photoUrl: String?,
    @SerializedName("noidung")
    @Expose
    val content: String?
)