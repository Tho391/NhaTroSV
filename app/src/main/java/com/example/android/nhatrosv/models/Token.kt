package com.example.android.nhatrosv.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Token(
    @Expose
    @SerializedName("access_token")
    var accessToken: String) {

    fun getToken(): String {
        return "bearer $accessToken"
    }
}