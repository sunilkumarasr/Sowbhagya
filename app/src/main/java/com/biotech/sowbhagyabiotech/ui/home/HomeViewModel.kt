package com.biotech.sowbhagyabiotech.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.biotech.sowbhagyabiotech.models.AddressResponseModel
import com.biotech.sowbhagyabiotech.models.BannerListModel
import com.biotech.sowbhagyabiotech.models.CategoriesListModel
import com.biotech.sowbhagyabiotech.models.ChangeStatusModel
import com.biotech.sowbhagyabiotech.models.DefaultResponseModel
import com.biotech.sowbhagyabiotech.models.MyOrdersListModel
import com.biotech.sowbhagyabiotech.models.SubCatProductsListModel
import com.biotech.sowbhagyabiotech.models.SubCategoriesListModel
import com.biotech.sowbhagyabiotech.models.WishListModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class HomeViewModel(var app: Application) : AndroidViewModel(app) {
    var bannerModelResponse = MutableLiveData<BannerListModel>()
    var categoryModelResponse = MutableLiveData<CategoriesListModel>()
    var subcatproductModelResponse = MutableLiveData<SubCatProductsListModel>()
    var subsearchproductModelResponse = MutableLiveData<SubCatProductsListModel>()
    var subbestproductModelResponse = MutableLiveData<SubCatProductsListModel>()
    var subcatModelResponse = MutableLiveData<SubCategoriesListModel>()
    var addressModelResponse = MutableLiveData<DefaultResponseModel>()
    var addAddressModelResponse = MutableLiveData<DefaultResponseModel>()
    var getaddressModelResponse = MutableLiveData<AddressResponseModel>()
    var getwishlistModelResponse = MutableLiveData<WishListModel>()
    var myorderslistModelResponse = MutableLiveData<MyOrdersListModel>()
    var wishlistsaveModelResponse = MutableLiveData<DefaultResponseModel>()
    var changeStatusModelResponse = MutableLiveData<ChangeStatusModel>()
    private val categoryRepository = HomeRepository()


    fun allbanners(
        mContext: Context
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.allbanners(
                mContext, bannerModelResponse
            )
        } else {
            val response = BannerListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            bannerModelResponse.value = response
        }

    }


    fun callcategoriesListApi(
        mContext: Context
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callgetCategoriesApi(
                mContext, categoryModelResponse
            )
        } else {
            val response = CategoriesListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            categoryModelResponse.value = response
        }

    }

    fun callsearchproductListApi(
        mContext: Context, page :String, searchWord: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callsearchproductlistApi(
                mContext, page = page, searchword = searchWord ,subsearchproductModelResponse
            )
        } else {
            val response = SubCatProductsListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            subsearchproductModelResponse.value = response
        }

    }
    fun callbestproductListApi(
        mContext: Context, type: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callgetbestproductlistApi(
                mContext, type = type, subbestproductModelResponse
            )
        } else {
            val response = SubCatProductsListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            subbestproductModelResponse.value = response
        }

    }
    fun callfeaturedroductListApi(
        mContext: Context, type: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callgetfeaturetproductlistApi(
                mContext, type = type, subbestproductModelResponse
            )
        } else {
            val response = SubCatProductsListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            subbestproductModelResponse.value = response
        }

    }
    fun callsubcatproductListApi(
        mContext: Context, subcatId: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callgetsubproductlistApi(
                mContext, subcatId = subcatId, subcatproductModelResponse
            )
        } else {
            val response = SubCatProductsListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            subcatproductModelResponse.value = response
        }

    }

    fun callsubcategoriesListApi(
        mContext: Context, category_id: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callgetsubcatlistApi(
                mContext, category_id, subcatModelResponse
            )
        } else {
            val response = SubCategoriesListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            subcatModelResponse.value = response
        }

    }

    fun calluseraddressApi(
        mContext: Context,
        user_id: String,
        full_name: String,
        email_id: String,
        mobile_number: String,
        pincode: String,
        address: String,
        street_address: String,
        apartment: String,
        city: String,
        state: String,
        country: String,
        address_id: String,
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.calluseraddressApi(
                mContext,
                user_id = user_id,
                full_name,
                email_id,
                mobile_number,
                pincode,
                address,
                street_address,
                apartment,
                city,
                state,
                country,
                address_id,

                addressModelResponse
            )
        } else {
            val response = DefaultResponseModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            addressModelResponse.value = response
        }

    }

    fun callAdduseraddressApi(
        mContext: Context,
        user_id: String,
        full_name: String,
        email_id: String,
        mobile_number: String,
        pincode: String,
        address: String,
        street_address: String,
        apartment: String,
        city: String,
        state: String,
        country: String,
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callAdduseraddressApi(
                mContext,
                user_id = user_id,
                full_name,
                email_id,
                mobile_number,
                pincode,
                address,
                street_address,
                apartment,
                city,
                state,
                country,
                addAddressModelResponse
            )
        } else {
            val response = DefaultResponseModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            addAddressModelResponse.value = response
        }
    }


    fun callgetAddressListApi(
        mContext: Context, user_id: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.calladdresslistApi(
                mContext, user_id = user_id, getaddressModelResponse
            )
        } else {
            val response = AddressResponseModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            getaddressModelResponse.value = response
        }

    }
    fun deleteAddress(
        mContext: Context, user_id: String,address_id: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.deleteAddress(
                mContext, user_id = user_id,address_id, addressModelResponse
            )
        } else {
            val response = DefaultResponseModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            addressModelResponse.value = response
        }

    }

    fun callPlaceOrderApi(
        mContext: Context,
        user_id: String,
        product_ids: String,
        product_qtys: String,
        addressId: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callPlaceOrderApi(
                mContext,
                user_id = user_id,
                product_ids,
                product_qtys,
                addressId,
                addressModelResponse
            )
        } else {
            val response = DefaultResponseModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            addressModelResponse.value = response
        }

    }
    fun callgetWihListApi(
        mContext: Context,
        user_id: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callgetWishListApi(
                mContext,
                user_id = user_id,

                getwishlistModelResponse
            )
        } else {
            val response = WishListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            getwishlistModelResponse.value = response
        }

    }

  fun callmyordersListApi(
        mContext: Context,
        user_id: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callmyordershListApi(
                mContext,
                user_id = user_id,

                myorderslistModelResponse
            )
        } else {
            val response = MyOrdersListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            myorderslistModelResponse.value = response
        }

    }

    fun callwishlistSaveListApi(
        mContext: Context, user_id: String, product_id: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.callwishListSaveApi(
                mContext, user_id = user_id, product_id = product_id, wishlistsaveModelResponse
            )
        } else {
            val response = DefaultResponseModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            wishlistsaveModelResponse.value = response
        }

    }


    fun changeStatusApi(
        mContext: Context, orderid: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            categoryRepository.changeStatusApi(
                mContext, orderid = orderid, changeStatusModelResponse
            )
        } else {
            val response = ChangeStatusModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            changeStatusModelResponse.value = response
        }

    }



}