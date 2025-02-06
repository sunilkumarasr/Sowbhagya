package com.biotech.sowbhagyabiotech.retrofit

import com.google.gson.annotations.SerializedName

class Response<T> {

    @SerializedName("data")
    var data: T? = null

    @SerializedName("message")
    var message: String? = ""

    @SerializedName("status")
    var status: Boolean? = false


}
