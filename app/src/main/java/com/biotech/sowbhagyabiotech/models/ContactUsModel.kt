package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class ContactUsModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ContactUsResponseModel? =null,
    @SerializedName("code") var code: Int? = null

)

data class ContactUsResponseModel(

    @SerializedName("fullname") var fullname: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("subject") var subject: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("created_time") var created_time: String? = null,

)




