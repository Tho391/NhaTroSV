package com.example.android.nhatrosv.models

import android.graphics.Bitmap
import java.util.*

data class Apartment(
    var id: String,
    var name: String,
    var date: String,
    var phoneNumber: String,
    var price: Int,
    var state: Boolean,
    var description: String,
    var Comment: List<Comment>?,
    var address: String?,
    var listImage: List<Bitmap>?,
    var coordinate: Coordinate
)