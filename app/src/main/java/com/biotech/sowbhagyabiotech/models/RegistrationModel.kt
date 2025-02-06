package com.biotech.sowbhagyabiotech.models


import com.google.gson.annotations.SerializedName

data class RegistrationModel(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("Message") var message: String? = null,
    @SerializedName("Response") var response: RegistrationResponse = RegistrationResponse(),
    @SerializedName("Status") var status: Boolean? = null
)

data class RegistrationResponse(
    @SerializedName("city") var city: String? = null,
    @SerializedName("email_id") var emailId: String? = null,
    @SerializedName("hno") var hno: String? = null,
    @SerializedName("landmark") var landmark: String? = null,
    @SerializedName("locality") var locality: String? = null,
    @SerializedName("mobile_number") var mobileNumber: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("state") var state: String? = null
)
