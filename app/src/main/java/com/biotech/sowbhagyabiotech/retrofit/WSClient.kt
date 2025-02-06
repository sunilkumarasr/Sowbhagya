package com.biotech.sowbhagyabiotech.retrofit


import android.content.Context
import android.content.Intent
import com.biotech.sankalpleaders.retrofit.IFailureHandler
import com.biotech.sankalpleaders.retrofit.ISuccessHandler
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WSClient<T> {

    init {
        if (requestQue == null) {
            requestQue = HashMap<String, String>()
        }
    }

    fun request(
        mContext: Context,
        requestId: Int,
        call: Call<T>?,
        mSuccessHandler: ISuccessHandler<T>?,
        mFailureHandler: IFailureHandler?
    ) {


        call?.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.body() != null) {
                    response.body()?.let {
                        mSuccessHandler?.successResponse(requestId, it)
                    }
                } else {
                    if (response.code() == 401) {
                        val intent = Intent(ApiConstants.AUTHENTICATION_FAILURE)
                        mContext.sendBroadcast(intent)
                    } else {
                        mFailureHandler?.failureResponse(
                            requestId,
                            mContext.getString(R.string.server_not_responding_please_try_later)
                        )
                    }

                    call.cancel()
                }


            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.message?.let {
                    mFailureHandler?.failureResponse(
                        requestId,
                        it
                    )
                }
                call.cancel()
            }
        })

    }


    companion object {
        var requestQue: HashMap<*, *>? = null
    }

}