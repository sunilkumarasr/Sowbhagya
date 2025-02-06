package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class BannerListModel(

    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<BannersResponseModel> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class BannersResponseModel(
    @SerializedName("slider_image") var slider_image: String? = null,
)
