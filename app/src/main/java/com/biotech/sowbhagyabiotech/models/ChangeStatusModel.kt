package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class ChangeStatusModel(

    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("code") var code: Int? = null

)
