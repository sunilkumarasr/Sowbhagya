package com.biotech.sowbhagyabiotech.ui.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.biotech.sowbhagyabiotech.models.ContactUsModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods


class ContactUsViewModel (var app: Application) : AndroidViewModel(app) {
    var contactUsModel = MutableLiveData<ContactUsModel>()

    val contactUsRepository= ContactUsRepository()

    fun callNotificationsApi(
        mContext: Context, fullname: String,
        email: String,
        mobile: String,
        subject: String,
        message: String,
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            contactUsRepository.callContactUsApi(
                mContext, fullname,email,mobile,subject, message, contactUsModel
            )
        } else {
            val response = ContactUsModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            contactUsModel.value = response
        }

    }



}