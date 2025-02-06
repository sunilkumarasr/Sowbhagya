package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class CategoriesListModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<CategoriesResponseModel> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class CategoriesResponseModel(

    @SerializedName("categories_id") var categoriesId: String? = null,
    @SerializedName("category_name") var categoryName: String? = null,
    @SerializedName("category_image") var categoryImage: String? = null

)




