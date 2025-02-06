package com.biotech.sowbhagyabiotech.authentication

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.biotech.sankalpleaders.retrofit.IFailureHandler
import com.biotech.sankalpleaders.retrofit.ISuccessHandler
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.models.LoginModel
import com.biotech.sowbhagyabiotech.models.OtpModel
import com.biotech.sowbhagyabiotech.models.PrivacyPolicyModel
import com.biotech.sowbhagyabiotech.models.ProfileModel
import com.biotech.sowbhagyabiotech.models.ReturnPolicyModel
import com.biotech.sowbhagyabiotech.models.ShippingPolicyModel
import com.biotech.sowbhagyabiotech.models.TermsAndConditionsModel
import com.biotech.sowbhagyabiotech.retrofit.ApiClient
import com.biotech.sowbhagyabiotech.retrofit.WSClient


class LoginRepository {
    fun callLogintApi(
        mContext: Context, emailId: String, firstResponseString: MutableLiveData<LoginModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callLoginApi(
            ApiConstants.API_KEY, emailId
        )

        WSClient<LoginModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<LoginModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: LoginModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = LoginModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
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
                override fun successResponse(requestCode: Int, mResponse: OtpModel) {
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

    fun callProfileApi(
        mContext: Context,
        userId: String,
        name: String,
        email: String,
        mobile: String,
       profile_image_name: String,
        profile_image_file: String,
        hno: String,
        landmark: String,
        locality: String,
        city: String,
        state: String,
        pincode: String,
        firstResponseString: MutableLiveData<ProfileModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callupdateProfileApi(
            ApiConstants.API_KEY, user_id = userId, name, email,mobile, profile_image_name, profile_image_file, hno,landmark,locality,city,state,pincode
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

    fun callgetProfileApi(
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

    fun shippingPolicyApi(mContext: Context, firstResponseString: MutableLiveData<ShippingPolicyModel>) {
        val call = ApiClient.getRetrofitInterface()?.shippingPolicyApi(
            ApiConstants.API_KEY,
        )

        WSClient<ShippingPolicyModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<ShippingPolicyModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: ShippingPolicyModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = ShippingPolicyModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }



    fun returnPolicyApi(mContext: Context, firstResponseString: MutableLiveData<ReturnPolicyModel>) {
        val call = ApiClient.getRetrofitInterface()?.returnPolicyApi(
            ApiConstants.API_KEY,
        )

        WSClient<ReturnPolicyModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<ReturnPolicyModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: ReturnPolicyModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = ReturnPolicyModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }


    fun termsconditionsApi(mContext: Context, firstResponseString: MutableLiveData<TermsAndConditionsModel>) {
        val call = ApiClient.getRetrofitInterface()?.termsconditionsApi(
            ApiConstants.API_KEY,
        )

        WSClient<TermsAndConditionsModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<TermsAndConditionsModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: TermsAndConditionsModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = TermsAndConditionsModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }

    fun PrivacyPolicyApi(mContext: Context, firstResponseString: MutableLiveData<PrivacyPolicyModel>) {
        val call = ApiClient.getRetrofitInterface()?.PrivacyPolicyApi(
            ApiConstants.API_KEY,
        )

        WSClient<PrivacyPolicyModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<PrivacyPolicyModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: PrivacyPolicyModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = PrivacyPolicyModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }



}