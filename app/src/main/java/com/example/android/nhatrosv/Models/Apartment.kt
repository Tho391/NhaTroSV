package com.example.android.nhatrosv.Models

import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey

class Apartment : ViewModel() {
    private lateinit var id: String
    private var name: String = ""
    private var phoneNumber: String = ""
    private var price: Int = 0
    private var state: Boolean = false
    private var description: String = ""
    private var Comment: List<Comment>? = null
    private var address: String? = null
    private var listImage: List<String>? = null
    private lateinit var coordinate: Coordinate
}