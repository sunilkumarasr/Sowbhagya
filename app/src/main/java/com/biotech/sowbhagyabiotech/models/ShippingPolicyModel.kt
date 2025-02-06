package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class ShippingPolicyModel(

    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<ShippingPolicyResponse> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class ShippingPolicyResponse(
    @SerializedName("shipping") var shipping: String? = null,
)
