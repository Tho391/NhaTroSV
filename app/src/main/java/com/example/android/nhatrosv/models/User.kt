package com.example.android.nhatrosv.models

import java.io.Serializable

data class User(
    var id: String?,
    var idToken: String?,
    var name: String?,
    var email: String?,
    var phoneNumber: String?,
    var photoUrl: String?,
    var username: String?,
    val password: String?
) : Serializable
