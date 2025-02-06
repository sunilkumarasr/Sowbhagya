package com.biotech.sowbhagyabiotech.utils

import com.biotech.sowbhagyabiotech.retrofit.RetrofitInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Const {



    val QRCODE_URL_WS = "https://ritps.com/sowbhagya/api/User_api"


    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(80000, TimeUnit.SECONDS)
        .connectTimeout(80000, TimeUnit.SECONDS)
        .build()


    val qrCode_uat = Retrofit.Builder()
        .baseUrl(QRCODE_URL_WS)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create<RetrofitInterface>(RetrofitInterface::class.java)


}