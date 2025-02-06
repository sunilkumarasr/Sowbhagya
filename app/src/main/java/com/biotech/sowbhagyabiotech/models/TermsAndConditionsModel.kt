package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class TermsAndConditionsModel(
    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<TermsAndConditionsResponse> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class TermsAndConditionsResponse(
    @SerializedName("terms_conditions") var terms_conditions: String? = null,

)
