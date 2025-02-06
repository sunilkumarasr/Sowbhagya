package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class ProductDetailsModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response") var Response: ArrayList<ProductDetailsResponseModel> = arrayListOf(),
    @SerializedName("code") var code: Int? = null

)

data class ProductDetailsResponseModel(

    @SerializedName("products_id") var products_id: String? = null,
    @SerializedName("product_num") var product_num: String? = null,
    @SerializedName("product_name") var product_name: String? = null,
    @SerializedName("product_title") var product_title: String? = null,
    @SerializedName("search_options") var search_options: String? = null,
    @SerializedName("product_description") var product_description: String? = null,
    @SerializedName("product_information") var product_information: String? = null,
    @SerializedName("categories_id") var categories_id: String? = null,
    @SerializedName("sub_categories_id") var sub_categories_id: String? = null,
    @SerializedName("product_image") var product_dimage: String? = null,
    @SerializedName("product_video") var product_video: String? = null,
    @SerializedName("slider_images") var slider_images: String? = null,
    @SerializedName("feautured") var feautured: String? = null,
    @SerializedName("best_seller") var best_seller: String? = null,
    @SerializedName("sales_price") var sales_price: String? = null,
    @SerializedName("offer_price") var offer_price: String? = null,
    @SerializedName("sku_code") var sku_code: String? = null,
    @SerializedName("stock") var stock: String? = null,
    @SerializedName("stock_count") var stock_count: String? = null,
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("tax_percentage") var tax_percentage: String? = null,
    @SerializedName("meta_title") var meta_title: String? = null,
    @SerializedName("meta_keywords") var meta_keywords: String? = null,
    @SerializedName("meta_description") var meta_description: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("created_date") var created_date: String? = null,
    @SerializedName("updated_date") var updated_date: String? = null,
    @SerializedName("product_image_full_path") var product_image_full_path: String? = null,

)




