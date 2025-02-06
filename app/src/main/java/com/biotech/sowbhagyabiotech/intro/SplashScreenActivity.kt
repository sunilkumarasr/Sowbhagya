package com.biotech.sowbhagyabiotech.intro


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.ActivitySplashBinding
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.utils.ConstantUtils

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ConstantUtils.changeNotificationBarColor(this, ContextCompat.getColor(this, R.color.notification_bar_blue), false)
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this, "sowbhagya")
        val isLogin = checkUserLoggedIn()
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLogin) {
                val intent = Intent(this, Home_Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, GettingStartedActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000)
    }


    fun checkUserLoggedIn(): Boolean {
        return securedSharedPreferenceWrapper.getBoolean(ApiConstants.USER_LOGGED_IN, false)
    }
}