package com.biotech.sowbhagyabiotech.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var mRetrofitInterface: RetrofitInterface? = null
    private val BASE_URL = "https://sowbhagyabiotech.in/api/User_api/"
    //private val BASE_URL = "https://ritps.com/sowbhagya/api/User_api/"

    fun getRetrofitInterface(): RetrofitInterface? {
        if (mRetrofitInterface == null) {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)


            httpClient.callTimeout(5, TimeUnit.MINUTES)
            httpClient.readTimeout(5, TimeUnit.MINUTES)
            httpClient.writeTimeout(5, TimeUnit.MINUTES)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            mRetrofitInterface = retrofit.create(RetrofitInterface::class.java)
        }

        return mRetrofitInterface


    }


}