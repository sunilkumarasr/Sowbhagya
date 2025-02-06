package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class NotificationsListModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<NotificationsResponseModel> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class NotificationsResponseModel(

    @SerializedName("id") var id: String? = null,
    @SerializedName("user_id") var user_id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("time") var time: String? = null

)




