package com.biotech.sowbhagyabiotech.authentication

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.biotech.sowbhagyabiotech.models.LoginModel
import com.biotech.sowbhagyabiotech.models.OtpModel
import com.biotech.sowbhagyabiotech.models.PrivacyPolicyModel
import com.biotech.sowbhagyabiotech.models.ProfileModel
import com.biotech.sowbhagyabiotech.models.ReturnPolicyModel
import com.biotech.sowbhagyabiotech.models.ShippingPolicyModel
import com.biotech.sowbhagyabiotech.models.TermsAndConditionsModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class LoginViewModel(var app: Application) : AndroidViewModel(app) {

    var loginModelResponse = MutableLiveData<LoginModel>()
    var otpModelResponse = MutableLiveData<OtpModel>()
    var profileModelResponse = MutableLiveData<ProfileModel>()
    var getprofileModelResponse = MutableLiveData<ProfileModel>()
    var shippingPolicy = MutableLiveData<ShippingPolicyModel>()
    var returnPolicyA = MutableLiveData<ReturnPolicyModel>()
    var termsconditionsA = MutableLiveData<TermsAndConditionsModel>()
    var privacyPolicyA = MutableLiveData<PrivacyPolicyModel>()
    private val loginRepository = LoginRepository()


    fun callLoginApi(
        mContext: Context,
        emailId: String,
        ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.callLogintApi(
                mContext,

                emailId,

                loginModelResponse
            )
        } else {
            val response = LoginModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            loginModelResponse.value = response
        }

    }

    fun callOtpApi(mContext: Context, userId: String, mobile: String, otp: String) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.callOtpApi(mContext, userId, mobile, otp, otpModelResponse)
        } else {
            val response = OtpModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            otpModelResponse.value = response
        }

    }
    fun callProfileApi(
        mContext: Context,
        userId: String, name: String,   email_id: String, mobile: String, profile_image_name: String,
        profile_image_file: String, hno: String,
        landmark: String,
        locality: String,
        city: String,
        state: String,
        pincode: String,

        ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.callProfileApi(
                mContext, userId, name,  email_id,mobile, profile_image_name, profile_image_file,hno,landmark,locality,city,state,pincode, profileModelResponse
            )
        } else {
            val response = ProfileModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            profileModelResponse.value = response
        }

    }
  fun callgetProfileApi(
        mContext: Context,
        userId: String

        ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.callgetProfileApi(
                mContext, userId,getprofileModelResponse
            )
        } else {
            val response = ProfileModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            getprofileModelResponse.value = response
        }

    }


    fun shippingPolicyApi(
        mContext: Context,
        ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.shippingPolicyApi(
                mContext,shippingPolicy
            )
        } else {
            val response = ShippingPolicyModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            shippingPolicy.value = response
        }

    }


    fun returnPolicyApi(
        mContext: Context,
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.returnPolicyApi(
                mContext,returnPolicyA
            )
        } else {
            val response = ReturnPolicyModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            returnPolicyA.value = response
        }

    }

    fun termsconditionsApi(
        mContext: Context,
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.termsconditionsApi(
                mContext,termsconditionsA
            )
        } else {
            val response = TermsAndConditionsModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            termsconditionsA.value = response
        }

    }


    fun PrivacyPolicyApi(
        mContext: Context,
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.PrivacyPolicyApi(
                mContext,privacyPolicyA
            )
        } else {
            val response = PrivacyPolicyModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            privacyPolicyA.value = response
        }

    }


}