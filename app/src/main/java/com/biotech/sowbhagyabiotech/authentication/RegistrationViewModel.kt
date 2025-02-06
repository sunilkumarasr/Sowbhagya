package com.biotech.sowbhagyabiotech.authentication

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.biotech.sowbhagyabiotech.models.OtpModel
import com.biotech.sowbhagyabiotech.models.ProfileModel
import com.biotech.sowbhagyabiotech.models.RegistrationModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class RegistrationViewModel(var app: Application) : AndroidViewModel(app) {

    var registrationModelResponse = MutableLiveData<RegistrationModel>()
    var otpModelResponse = MutableLiveData<OtpModel>()
    var profileModelResponse = MutableLiveData<ProfileModel>()
    var getprofileModelResponse = MutableLiveData<ProfileModel>()
    private val registrationRepository = RegistrationRepository()


    fun callRegistrationApi(
        mContext: Context,
        mobile_number: String,
        name: String,
        email_id: String,
        hno: String,
        landmark: String,
        locality: String,
        city: String,
        state: String,
        pincode: String,

        ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            registrationRepository.callRegistrationApi(
                mContext,
                mobile_number,
                name,
                email_id,
                hno,
                landmark,
                locality,
                city,
                state,
                pincode,
                registrationModelResponse
            )
        } else {
            val response = RegistrationModel()
            response.message = "No Internet Access"
            response.status = false
            response.code = 0
            registrationModelResponse.value = response
        }

    }



}