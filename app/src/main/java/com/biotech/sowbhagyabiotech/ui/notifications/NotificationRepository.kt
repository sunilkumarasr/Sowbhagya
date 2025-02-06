package com.biotech.sowbhagyabiotech.ui.notifications

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.biotech.sankalpleaders.retrofit.IFailureHandler
import com.biotech.sankalpleaders.retrofit.ISuccessHandler
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.models.NotificationsListModel
import com.biotech.sowbhagyabiotech.retrofit.ApiClient
import com.biotech.sowbhagyabiotech.retrofit.WSClient


class NotificationRepository {
    fun callNotificationsApi(
        mContext: Context,
        user_id: String,firstResponseString: MutableLiveData<NotificationsListModel>
    ) {

        val call = ApiClient.getRetrofitInterface()?.callNotificationsApi(
            ApiConstants.API_KEY, user_id
        )

        WSClient<NotificationsListModel>().request(mContext,
            ApiConstants.First_Api_Call,
            call,
            object : ISuccessHandler<NotificationsListModel> {
                override fun successResponse(
                    requestCode: Int, mResponse: NotificationsListModel
                ) {
                    firstResponseString.value = mResponse
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    val response = NotificationsListModel()/*  response.settings!!.success = WsConstants.STATUS_FAILURE
                      response.settings!!.message = message*/
                    firstResponseString.value = response

                }
            })
    }




}