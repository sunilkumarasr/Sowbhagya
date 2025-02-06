package com.biotech.sowbhagyabiotech.ui.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.biotech.sankalpleaders.retrofit.IFailureHandler
import com.biotech.sankalpleaders.retrofit.ISuccessHandler
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.models.ContactUsModel
import com.biotech.sowbhagyabiotech.retrofit.ApiClient
import com.biotech.sowbhagyabiotech.retrofit.WSClient


class ContactUsRepository {
    fun callContactUsApi(
        mContext: Context,
       fullname: String,
        email: String,
        mobile: String,
        subject: String,
         message: String,firstResponseString: MutableLiveData<ContactUsModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callContactUsApi(
            ApiConstants.API_KEY, fullname, email, mobile, subject, message
        )

        WSClient<ContactUsModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<ContactUsModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: ContactUsModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = ContactUsModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }




}