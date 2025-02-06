package com.biotech.sowbhagyabiotech.authentication

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.biotech.sankalpleaders.retrofit.IFailureHandler
import com.biotech.sankalpleaders.retrofit.ISuccessHandler
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.models.OtpModel
import com.biotech.sowbhagyabiotech.models.ProfileModel
import com.biotech.sowbhagyabiotech.models.RegistrationModel
import com.biotech.sowbhagyabiotech.retrofit.ApiClient
import com.biotech.sowbhagyabiotech.retrofit.WSClient


class RegistrationRepository {
    fun callRegistrationApi(
        mContext: Context, mobile_number: String,
        name: String,
        email_id: String,
       hno: String,
         landmark: String,
        locality: String,
        city: String,
         state: String,
         pincode: String, firstResponseString: MutableLiveData<RegistrationModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callRegistrationinApi(
            ApiConstants.API_KEY, mobile_number, name, email_id, hno, landmark, locality, city, state, pincode
        )

        WSClient<RegistrationModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<RegistrationModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: RegistrationModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = RegistrationModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }


    fun callOtpApi(
        mContext: Context,
        userId: String,
        mobile: String,
        otp: String,
        firstResponseString: MutableLiveData<OtpModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callOtpApi(
            ApiConstants.API_KEY, user_id = userId, mobile, otp
        )

        WSClient<OtpModel>().request(mContext,
            ApiConstants.Otp_Api_Call,
            call,
            object : ISuccessHandler<OtpModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: OtpModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = OtpModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    /*fun callProfileApi(
        mContext: Context,
        userId: String,
        name: String,
        email: String,
        mobile: String,
        profile_image_name: String,
        profile_image_file: String,
        firstResponseString: MutableLiveData<ProfileModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callupdateProfileApi(
            ApiConstants.API_KEY, user_id = userId, name, email, mobile, profile_image_name, profile_image_file
        )

        WSClient<ProfileModel>().request(mContext,
            ApiConstants.Otp_Api_Call,
            call,
            object : ISuccessHandler<ProfileModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: ProfileModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = ProfileModel()*//*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*//*
                    firstResponseString.value = response

                }
            })
    }*/ fun callgetProfileApi(
        mContext: Context,
        userId: String,

        firstResponseString: MutableLiveData<ProfileModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callgetProfileApi(
            ApiConstants.API_KEY, user_id = userId
        )

        WSClient<ProfileModel>().request(mContext,
            ApiConstants.Otp_Api_Call,
            call,
            object : ISuccessHandler<ProfileModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: ProfileModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = ProfileModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }
}