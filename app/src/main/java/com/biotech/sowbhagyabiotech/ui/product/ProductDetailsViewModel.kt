package com.biotech.sowbhagyabiotech.ui.product

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.biotech.sowbhagyabiotech.models.ProductDetailsModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods


class ProductDetailsViewModel (var app: Application) : AndroidViewModel(app) {
    var productDetailsResponseModel = MutableLiveData<ProductDetailsModel>()

    private val productDetailsRepository = ProductDetailsRepository()

    fun callProductDetailsApi(
        mContext: Context, productId: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            productDetailsRepository.callProductDetailsApi(
                mContext, productId, productDetailsResponseModel
            )
        } else {
            val response = ProductDetailsModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            productDetailsResponseModel.value = response
        }

    }



}