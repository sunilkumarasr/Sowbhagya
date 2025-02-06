package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class ReturnPolicyModel(

    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<ReturnPolicyResponse> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class ReturnPolicyResponse(
    @SerializedName("return_policy") var return_policy: String? = null,

)
