package com.biotech.sowbhagyabiotech.models

import com.google.gson.annotations.SerializedName

data class MyOrdersListModel(


    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Response" ) var Response : MyOrdersResponseModel? = MyOrdersResponseModel(),
    @SerializedName("code") var code: Int? = null

)

data class MyOrdersResponseModel(
    @SerializedName("active_orders") var activeOrders: ArrayList<ActiveOrders> = arrayListOf(),
    @SerializedName("past_orders") var pastOrders: ArrayList<PastOrders> = arrayListOf()

)

data class PastOrders(

    @SerializedName("order_id") var orderId: String? = null,
    @SerializedName("order_number") var orderNumber: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("product_details") var productDetails: ArrayList<ProductDetails> = arrayListOf(),
    @SerializedName("address_details") var addressDetails: AddressDetails? = AddressDetails(),
    @SerializedName("grand_total") var grandTotal: String? = null,
    @SerializedName("delivery_status") var deliveryStatus: String? = null,
    @SerializedName("created_date") var createdDate: String? = null,
    @SerializedName("order_date") var orderDate: String? = null

)

data class AddressDetails(

    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("mobile_number") var mobileNumber: String? = null,
    @SerializedName("email_id") var emailId: String? = null,
    @SerializedName("pincode") var pincode: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("street_address") var streetAddress: String? = null,
    @SerializedName("apartment") var apartment: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("country") var country: String? = null

)


data class ProductDetails(

    @SerializedName("products_id") var productsId: String? = null,
    @SerializedName("product_name") var productName: String? = null,
    @SerializedName("qty") var qty: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("image") var image: String? = null

)

data class ActiveOrders(

    @SerializedName("order_id") var orderId: String? = null,
    @SerializedName("order_number") var orderNumber: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("product_details") var productDetails: ArrayList<ProductDetails> = arrayListOf(),
    @SerializedName("address_details") var addressDetails: AddressDetails? = AddressDetails(),
    @SerializedName("grand_total") var grandTotal: String? = null,
    @SerializedName("delivery_status") var deliveryStatus: String? = null,
    @SerializedName("created_date") var createdDate: String? = null,
    @SerializedName("updated_date") var updatedDate: String? = null,
    @SerializedName("order_date") var orderDate: String? = null

)
