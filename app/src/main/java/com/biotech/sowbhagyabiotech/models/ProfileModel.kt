package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class ProfileModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ProfileResponse? = ProfileResponse(),
    @SerializedName("code") var code: Int? = null

)

data class ProfileResponse(
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("registration_number") var registration_number: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email_id") var emailId: String? = null,
    @SerializedName("hno") var hno: String? = null,
    @SerializedName("landmark") var landmark: String? = null,
    @SerializedName("locality") var locality: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("pincode") var pincode: String? = null,
    @SerializedName("mobile_number") var mobileNumber: String? = null,
    @SerializedName("profile_image") var profile_image: String? = null,
    @SerializedName("profile_image_full_url") var profile_image_full_url: String? = null,
)



