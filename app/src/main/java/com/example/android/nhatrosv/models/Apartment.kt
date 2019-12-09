package com.example.android.nhatrosv.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Apartment(
    @SerializedName("idNhaTro")
    @Expose
    val id: Int?,
    @SerializedName("TenChuTro")
    @Expose
    val name: String?,
    @SerializedName("Sdt")
    @Expose
    val phoneNumber: String?,
    @SerializedName("TenDuong")
    @Expose
    val street: String?,
    @SerializedName("TenQuan")
    @Expose
    val district: String?,
    @SerializedName("TenTP")
    @Expose
    val city: String?,
    @SerializedName("gia")
    @Expose
    val price: Int?,
    @SerializedName("dientich")
    @Expose
    val area: Int?,
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("ImageHinh")
    @Expose
    val imageURl: String?,
    @SerializedName("localX")
    @Expose
    val localX: Double?,
    @SerializedName("localY")
    @Expose
    val localY: Double?

) : Serializable {

    fun getInfo(): String {
        var result =""
        result = "Giá: $price\n Diện tích: $area"
        return result
    }
}