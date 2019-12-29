package com.example.android.nhatrosv.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseAddComment(
    @SerializedName("fieldCount")
    @Expose
    var fieldCount: Int? = null,
    @SerializedName("affectedRows")
    @Expose
    var affectedRows: Int? = null,
    @SerializedName("insertId")
    @Expose
    var insertId: Int? = null,
    @SerializedName("serverStatus")
    @Expose
    var serverStatus: Int? = null,
    @SerializedName("warningCount")
    @Expose
    var warningCount: Int? = null,
    @SerializedName("message")
    @Expose
    var message: String? = null,
    @SerializedName("protocol41")
    @Expose
    var protocol41: Boolean? = null,
    @SerializedName("changedRows")
    @Expose
    var changedRows: Int? = null
)