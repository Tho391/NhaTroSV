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
    @SerializedName("DiaChi")
    @Expose
    val address: String?,
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
    @SerializedName("Img")
    @Expose
    val imageURl: String?,
    @SerializedName("localX")
    @Expose
    val localX: Double?,
    @SerializedName("localY")
    @Expose
    val localY: Double?,
    @SerializedName("chitiet")
    @Expose
    val details: String?
) : Serializable {

    fun getInfo(): String {
        //return "Giá: $price\nDiện tích: $area m\u00B2\nSố điện thoại: $phoneNumber"
        return "Giá: $price\nDiện tích: $area m\u00B2"
    }
}
