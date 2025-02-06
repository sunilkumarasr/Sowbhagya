package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class SubCatProductsListModel(


    @SerializedName("Status"   ) var Status   : Boolean?  = null,
    @SerializedName("Message"  ) var Message  : String?   = null,
    @SerializedName("Response" ) var Response : ArrayList<SubCatProductsResponseModel> = arrayListOf(),
    @SerializedName("code"     ) var code     : Int?      = null

)
data class SubCatProductsResponseModel(

    @SerializedName("products_id"   ) var productsId   : String? = null,
    @SerializedName("product_num"   ) var productNum   : String? = null,
    @SerializedName("product_name"  ) var productName  : String? = null,
    @SerializedName("sales_price"   ) var salesPrice   : String? = null,
    @SerializedName("offer_price"   ) var offerPrice   : String? = null,
    @SerializedName("product_image" ) var productImage : String? = null,
    @SerializedName("tax_percentage" ) var tax_percentage : String? = null,

)




