package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class SubCategoriesListModel(


    @SerializedName("Status"   ) var Status   : Boolean?  = null,
    @SerializedName("Message"  ) var Message  : String?   = null,
    @SerializedName("Response" ) var Response : ArrayList<SubCategoriesResponseModel> = arrayListOf(),
    @SerializedName("code"     ) var code     : Int?      = null

)
data class SubCategoriesResponseModel(

    @SerializedName("sub_categories_id" ) var subCategoriesId : String? = null,
    @SerializedName("sub_category_name" ) var subCategoryName : String? = null,
    @SerializedName("subcat"        ) var subcat       : Int?                = null

)




