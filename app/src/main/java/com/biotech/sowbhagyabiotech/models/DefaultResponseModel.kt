package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class DefaultResponseModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: String? = null,
    @SerializedName("code") var code: Int? = null

)

data class AddressResponseModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<AddressListResponse> = arrayListOf<AddressListResponse>(),
    @SerializedName("code") var code: Int? = null

)

data class AddressListResponse(

    @SerializedName("address_id") var addressId: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("mobile_number") var mobileNumber: String? = null,
    @SerializedName("email_id") var emailId: String? = null,
    @SerializedName("pincode") var pincode: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("street_address") var streetAddress: String? = null,
    @SerializedName("apartment") var apartment: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("created_date") var createdDate: String? = null,
    @SerializedName("updated_date") var updatedDate: String?=null

)



