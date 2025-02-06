package com.biotech.sowbhagyabiotech.retrofit

import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.models.AddressResponseModel
import com.biotech.sowbhagyabiotech.models.BannerListModel
import com.biotech.sowbhagyabiotech.models.CategoriesListModel
import com.biotech.sowbhagyabiotech.models.ChangeStatusModel
import com.biotech.sowbhagyabiotech.models.ContactUsModel
import com.biotech.sowbhagyabiotech.models.DefaultResponseModel
import com.biotech.sowbhagyabiotech.models.LoginModel
import com.biotech.sowbhagyabiotech.models.MyOrdersListModel
import com.biotech.sowbhagyabiotech.models.NotificationsListModel
import com.biotech.sowbhagyabiotech.models.OtpModel
import com.biotech.sowbhagyabiotech.models.PrivacyPolicyModel
import com.biotech.sowbhagyabiotech.models.ProductDetailsModel
import com.biotech.sowbhagyabiotech.models.ProfileModel
import com.biotech.sowbhagyabiotech.models.RegistrationModel
import com.biotech.sowbhagyabiotech.models.ReturnPolicyModel
import com.biotech.sowbhagyabiotech.models.ShippingPolicyModel
import com.biotech.sowbhagyabiotech.models.SubCatProductsListModel
import com.biotech.sowbhagyabiotech.models.SubCategoriesListModel
import com.biotech.sowbhagyabiotech.models.TermsAndConditionsModel
import com.biotech.sowbhagyabiotech.models.WishListModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitInterface {


    @FormUrlEncoded
    @POST(ApiConstants.LOGIN_WITH_EMAIL_ID)
    fun callLoginApi(
        @Header("api_key") api_key: String,
        @Field("email_id") emailId: String

    ): Call<LoginModel>

    @FormUrlEncoded
    @POST(ApiConstants.OTP_EMAIL_VERIFY)
    fun callOtpApi(@Header("api_key") api_key: String,@Field("user_id") user_id: String,@Field("email_id") mobile_number: String,@Field("otp") otp: String): Call<OtpModel>


    @POST(ApiConstants.shipping_policy)
    fun shippingPolicyApi(
        @Header("api_key") api_key: String
    ): Call<ShippingPolicyModel>

    @POST(ApiConstants.return_policy)
    fun returnPolicyApi(
        @Header("api_key") api_key: String
    ): Call<ReturnPolicyModel>

    @POST(ApiConstants.termsconditions)
    fun termsconditionsApi(
        @Header("api_key") api_key: String
    ): Call<TermsAndConditionsModel>

    @POST(ApiConstants.privacy_policy)
    fun PrivacyPolicyApi(
        @Header("api_key") api_key: String
    ): Call<PrivacyPolicyModel>


    @FormUrlEncoded
    @POST(ApiConstants.UpdateProfile_VERIFY)
    fun callupdateProfileApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String,
        @Field("name") name: String,
        @Field("email_id") email_id: String,
        @Field("mobile_number") mobile: String,
        @Field("profile_image_name") vprofile_image_name: String,
        @Field("profile_image_file") profile_image_file: String,
        @Field("hno") hno: String,
        @Field("landmark") landmark: String,
        @Field("locality") locality: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("pincode") pincode: String

        ): Call<ProfileModel>

    @FormUrlEncoded
    @POST(ApiConstants.GETProfile_VERIFY)
    fun callgetProfileApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String
        ): Call<ProfileModel>


    @POST(ApiConstants.ALLBANNERS)
    fun allbanners(
        @Header("api_key") api_key: String
    ): Call<BannerListModel>

    @POST(ApiConstants.CATEGORIESLIST)
    fun callcategoriesApi(
        @Header("api_key") api_key: String
        ): Call<CategoriesListModel>

    @FormUrlEncoded
    @POST(ApiConstants.BESTPRODUCTSLIST)
    fun callbestproductssApi(
        @Header("api_key") api_key: String,
        @Field("type") type: String,
        ): Call<SubCatProductsListModel>

    @FormUrlEncoded
    @POST(ApiConstants.SUBCATPRODUCTLIST)
    fun callsubcatproductssApi(
        @Header("api_key") api_key: String,
        @Field("page") page: String,
        @Field("subcategory_id") subcategory_id: String
        ): Call<SubCatProductsListModel>

    @FormUrlEncoded
    @POST(ApiConstants.SEARCHCATEGORIESLIST)
    fun callSearchproductApi(
        @Header("api_key") api_key: String,
        @Field("page") page: String,
        @Field("search_word") search_word: String
        ): Call<SubCatProductsListModel>

    @FormUrlEncoded
    @POST(ApiConstants.SUBCATEGORIESLIST)
    fun callsubcategorielistApi(
        @Header("api_key") api_key: String,
        @Field("category_id") category_id: String
        ): Call<SubCategoriesListModel>

    @FormUrlEncoded
    @POST("user_address_save")
    fun calluseraddressApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String,
        @Field("full_name") full_name: String,
        @Field("email_id") email_id: String,
        @Field("mobile_number") mobile_number: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("street_address") street_address: String,
        @Field("apartment") apartment: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("country") country: String,
        @Field("address_id") address_id: String
        ): Call<DefaultResponseModel>


    @FormUrlEncoded
    @POST("add_user_address")
    fun callAdduseraddressApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String,
        @Field("full_name") full_name: String,
        @Field("email_id") email_id: String,
        @Field("mobile_number") mobile_number: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("street_address") street_address: String,
        @Field("apartment") apartment: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("country") country: String
    ): Call<DefaultResponseModel>


    @FormUrlEncoded
    @POST("user_addresses_list")
    fun calladdresslistApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String
    ): Call<AddressResponseModel>
  @FormUrlEncoded
    @POST("user_delete_address")
    fun deleteAddress(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String,
        @Field("address_id") address_id: String
    ): Call<DefaultResponseModel>

    @FormUrlEncoded
    @POST("products_place_order")
    fun callproductsplaceOrderApi(
        @Header("api_key") api_key: String,
        @Field("product_ids") product_ids: String,
        @Field("product_qtys") product_qtys: String,
        @Field("user_id") user_id: String,
        @Field("address_id") address_id: String,
    ): Call<DefaultResponseModel>

    @FormUrlEncoded
    @POST("user_wishlist")
    fun callgetWishListApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String
    ): Call<WishListModel>

    @FormUrlEncoded
    @POST("user_orders_list")
    fun callgetOrdersListApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String
    ): Call<MyOrdersListModel>

    @FormUrlEncoded
    @POST("wishlist_save")
    fun callwishlistSaveApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String,
        @Field("product_id") product_id: String
    ): Call<DefaultResponseModel>


    @FormUrlEncoded
    @POST("change_status")
    fun changeStatusApi(
        @Header("api_key") api_key: String,
        @Field("order_number") orderid: String,
    ): Call<ChangeStatusModel>



    @FormUrlEncoded
    @POST(ApiConstants.REGISTRATION)
    fun callRegistrationinApi(
        @Header("api_key") api_key: String,
        @Field("mobile_number") mobile_number: String,
        @Field("name") name: String,
        @Field("email_id") email_id: String,
        @Field("hno") hno: String,
        @Field("landmark") landmark: String,
        @Field("locality") locality: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("pincode") pincode: String
    ): Call<RegistrationModel>

    @FormUrlEncoded
    @POST(ApiConstants.NOTIFICATIONS)
    fun callNotificationsApi(
        @Header("api_key") api_key: String,
        @Field("user_id") user_id: String,
    ): Call<NotificationsListModel>

    @FormUrlEncoded
    @POST(ApiConstants.PRODUCT_DETAILS)
    fun callProductDetailssApi(
        @Header("api_key") api_key: String,
        @Field("product_id") product_id: String,
    ): Call<ProductDetailsModel>

    @FormUrlEncoded
    @POST(ApiConstants.CONTACT_US)
    fun callContactUsApi(
        @Header("api_key") api_key: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("subject") subject: String,
        @Field("message") message: String,
    ): Call<ContactUsModel>

}