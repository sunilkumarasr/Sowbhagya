package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class WishListModel(


    @SerializedName("Status"   ) var Status   : Boolean?  = null,
    @SerializedName("Message"  ) var Message  : String?   = null,
    @SerializedName("Response" ) var Response : ArrayList<WishListResponseModel> = arrayListOf(),
    @SerializedName("code"     ) var code     : Int?      = null

)
data class WishListResponseModel(
    @SerializedName("id"            ) var id           : String? = null,
    @SerializedName("user_id"       ) var userId       : String? = null,
    @SerializedName("product_id"    ) var productId    : String? = null,
    @SerializedName("created_date"  ) var createdDate  : String? = null,
    @SerializedName("products_id"   ) var productsId   : String? = null,
    @SerializedName("product_num"   ) var productNum   : String? = null,
    @SerializedName("product_name"  ) var productName  : String? = null,
    @SerializedName("sales_price"   ) var salesPrice   : String? = null,
    @SerializedName("offer_price"   ) var offerPrice   : String? = null,
    @SerializedName("product_image" ) var productImage : String? = null

)




