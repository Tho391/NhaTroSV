package com.example.android.nhatrosv.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Response(
    @Expose
    @SerializedName("access_token")
    var accessToken: String?,
    @Expose
    @SerializedName("data")
    var data: String?
) : Serializable {

    fun getToken(): String {
        return "bearer $accessToken"
    }
}