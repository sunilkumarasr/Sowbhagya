package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class PrivacyPolicyModel(

    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<PrivacyPolicyResponse> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class PrivacyPolicyResponse(
    @SerializedName("privacy_policy") var privacy_policy: String? = null,

)
