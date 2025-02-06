package com.biotech.sowbhagyabiotech.ui.product

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.biotech.sankalpleaders.retrofit.IFailureHandler
import com.biotech.sankalpleaders.retrofit.ISuccessHandler
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.models.ProductDetailsModel
import com.biotech.sowbhagyabiotech.retrofit.ApiClient
import com.biotech.sowbhagyabiotech.retrofit.WSClient


class ProductDetailsRepository {
    fun callProductDetailsApi(
        mContext: Context,
        product_id: String,firstResponseString: MutableLiveData<ProductDetailsModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callProductDetailssApi(
            ApiConstants.API_KEY, product_id
        )

        WSClient<ProductDetailsModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<ProductDetailsModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: ProductDetailsModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = ProductDetailsModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }




}