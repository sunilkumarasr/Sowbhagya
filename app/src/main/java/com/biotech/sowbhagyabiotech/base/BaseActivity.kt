package com.biotech.sowbhagyabiotech.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper

abstract class BaseActivity : AppCompatActivity() {

    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    var isShowBottomBar = true
    var isShowAppBar = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this, "sowbhagya")
    }


    override fun onStart() {
        super.onStart()
        val decor = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }


    override fun onResume() {
        Log.d("onResume", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("onPause", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("onStop", "onStop")
        super.onStop()
    }


    override fun onRestart() {
        super.onRestart()
        Log.d("onRestart", "onRestart")
    }

    fun checkUserLoggedIn(): Boolean {
        return securedSharedPreferenceWrapper.getBoolean(ApiConstants.USER_LOGGED_IN, false)
    }


}