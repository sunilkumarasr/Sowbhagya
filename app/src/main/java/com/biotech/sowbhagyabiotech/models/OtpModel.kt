package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class OtpModel(


    @SerializedName("Status"   ) var Status   : Boolean?  = null,
    @SerializedName("Message"  ) var Message  : String?   = null,
    @SerializedName("Response" ) var Response : OtpResponse? = OtpResponse(),
    @SerializedName("code"     ) var code     : Int?      = null

)
data class OtpResponse(

    @SerializedName("user_id"       ) var userId       : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null
)



