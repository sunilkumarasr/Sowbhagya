package com.biotech.sowbhagyabiotech.ui.notifications

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.biotech.sowbhagyabiotech.models.NotificationsListModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods


class NotificationsViewModel (var app: Application) : AndroidViewModel(app) {
    var notificationResponseModel = MutableLiveData<NotificationsListModel>()

    private val notificationRepository = NotificationRepository()

    fun callNotificationsApi(
        mContext: Context, userId: String
    ) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            notificationRepository.callNotificationsApi(
                mContext, userId, notificationResponseModel
            )
        } else {
            val response = NotificationsListModel()
            response.Message = "No Internet Access"
            response.Status = false
            response.code = 0
            notificationResponseModel.value = response
        }

    }



}