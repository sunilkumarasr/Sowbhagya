package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class LoginModel(


    @SerializedName("Status"   ) var Status   : Boolean?  = null,
    @SerializedName("Message"  ) var Message  : String?   = null,
    @SerializedName("Response" ) var Response : LoginResponse? = LoginResponse(),
    @SerializedName("code"     ) var code     : Int?      = null

)
data class LoginResponse(

    @SerializedName("login_otp"     ) var loginOtp     : Int?    = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("email_id" ) var email_id : String? = null,
    @SerializedName("user_id"       ) var userId       : String? = null

)

data class LoginRequest(
    var mobile_number: String="",
)


