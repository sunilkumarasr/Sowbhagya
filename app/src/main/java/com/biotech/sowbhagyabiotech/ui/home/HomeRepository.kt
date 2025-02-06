package com.biotech.sowbhagyabiotech.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.biotech.sankalpleaders.retrofit.IFailureHandler
import com.biotech.sankalpleaders.retrofit.ISuccessHandler
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.models.AddressResponseModel
import com.biotech.sowbhagyabiotech.models.BannerListModel
import com.biotech.sowbhagyabiotech.models.CategoriesListModel
import com.biotech.sowbhagyabiotech.models.ChangeStatusModel
import com.biotech.sowbhagyabiotech.models.DefaultResponseModel
import com.biotech.sowbhagyabiotech.models.MyOrdersListModel
import com.biotech.sowbhagyabiotech.models.SubCatProductsListModel
import com.biotech.sowbhagyabiotech.models.SubCategoriesListModel
import com.biotech.sowbhagyabiotech.models.WishListModel
import com.biotech.sowbhagyabiotech.retrofit.ApiClient
import com.biotech.sowbhagyabiotech.retrofit.WSClient


class HomeRepository {

    //banner
    fun allbanners(
        mContext: Context, firstResponseString: MutableLiveData<BannerListModel>
    ) {
        val call = ApiClient.getRetrofitInterface()?.allbanners(
            ApiConstants.API_KEY
        )

        WSClient<BannerListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<BannerListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: BannerListModel
                ) {
                    firstResponseString.value = mResponse
                }
            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = BannerListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response
                }
            })
    }


    fun callgetCategoriesApi(
        mContext: Context, firstResponseString: MutableLiveData<CategoriesListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callcategoriesApi(
            ApiConstants.API_KEY
        )

        WSClient<CategoriesListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<CategoriesListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: CategoriesListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = CategoriesListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callsearchproductlistApi(
        mContext: Context,
        page: String,
        searchword: String,
        firstResponseString: MutableLiveData<SubCatProductsListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callSearchproductApi(
            ApiConstants.API_KEY, page, searchword
        )

        WSClient<SubCatProductsListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<SubCatProductsListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: SubCatProductsListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = SubCatProductsListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callgetbestproductlistApi(
        mContext: Context,
        type: String,
        firstResponseString: MutableLiveData<SubCatProductsListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callbestproductssApi(
            ApiConstants.API_KEY, type
        )

        WSClient<SubCatProductsListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<SubCatProductsListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: SubCatProductsListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = SubCatProductsListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callgetfeaturetproductlistApi(
        mContext: Context,
        type: String,
        firstResponseString: MutableLiveData<SubCatProductsListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callbestproductssApi(
            ApiConstants.API_KEY, type
        )

        WSClient<SubCatProductsListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<SubCatProductsListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: SubCatProductsListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = SubCatProductsListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callgetsubproductlistApi(
        mContext: Context,
        subcatId: String,
        firstResponseString: MutableLiveData<SubCatProductsListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callsubcatproductssApi(
            ApiConstants.API_KEY, "1", subcatId
        )

        WSClient<SubCatProductsListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<SubCatProductsListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: SubCatProductsListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = SubCatProductsListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callgetsubcatlistApi(
        mContext: Context,
        category_id: String,
        firstResponseString: MutableLiveData<SubCategoriesListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callsubcategorielistApi(
            ApiConstants.API_KEY, category_id
        )

        WSClient<SubCategoriesListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<SubCategoriesListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: SubCategoriesListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = SubCategoriesListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
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
        firstResponseString: MutableLiveData<DefaultResponseModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.calluseraddressApi(
            ApiConstants.API_KEY,
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
            address_id
        )

        WSClient<DefaultResponseModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<DefaultResponseModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: DefaultResponseModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = DefaultResponseModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
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
        firstResponseString: MutableLiveData<DefaultResponseModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callAdduseraddressApi(
            ApiConstants.API_KEY,
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
            country
        )

        WSClient<DefaultResponseModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<DefaultResponseModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: DefaultResponseModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = DefaultResponseModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }


    fun calladdresslistApi(
        mContext: Context,
        user_id: String,
        firstResponseString: MutableLiveData<AddressResponseModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.calladdresslistApi(
            ApiConstants.API_KEY, user_id = user_id
        )

        WSClient<AddressResponseModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<AddressResponseModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: AddressResponseModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = AddressResponseModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }
    fun deleteAddress(
        mContext: Context,
        user_id: String,
        address_id: String,
        firstResponseString: MutableLiveData<DefaultResponseModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.deleteAddress(
            ApiConstants.API_KEY, user_id = user_id, address_id = address_id
        )

        WSClient<DefaultResponseModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<DefaultResponseModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: DefaultResponseModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = DefaultResponseModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callPlaceOrderApi(
        mContext: Context,
        user_id: String,
        product_ids: String,
        product_qtys: String,
        addressId: String,
        firstResponseString: MutableLiveData<DefaultResponseModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callproductsplaceOrderApi(
            ApiConstants.API_KEY,
            user_id = user_id,
            product_ids = product_ids,
            product_qtys = product_qtys,
            address_id = addressId
        )

        WSClient<DefaultResponseModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<DefaultResponseModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: DefaultResponseModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = DefaultResponseModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callgetWishListApi(
        mContext: Context, user_id: String,

        firstResponseString: MutableLiveData<WishListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callgetWishListApi(
            ApiConstants.API_KEY,
            user_id = user_id,

            )

        WSClient<WishListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<WishListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: WishListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = WishListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun callmyordershListApi(
        mContext: Context, user_id: String,

        firstResponseString: MutableLiveData<MyOrdersListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callgetOrdersListApi(
            ApiConstants.API_KEY,
            user_id = user_id,

            )

        WSClient<MyOrdersListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<MyOrdersListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: MyOrdersListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = MyOrdersListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }
    fun callwishListSaveApi(
        mContext: Context, user_id: String,product_id : String,

        firstResponseString: MutableLiveData<DefaultResponseModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callwishlistSaveApi(
            ApiConstants.API_KEY,
            user_id = user_id,
            product_id = product_id

            )

        WSClient<DefaultResponseModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<DefaultResponseModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: DefaultResponseModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = DefaultResponseModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }


    fun changeStatusApi(
        mContext: Context, orderid: String,

        firstResponseString: MutableLiveData<ChangeStatusModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.changeStatusApi(
            ApiConstants.API_KEY,
            orderid = orderid
        )

        WSClient<ChangeStatusModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<ChangeStatusModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: ChangeStatusModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = ChangeStatusModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }


}