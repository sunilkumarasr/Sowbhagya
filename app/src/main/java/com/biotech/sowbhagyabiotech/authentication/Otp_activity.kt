package com.biotech.sowbhagyabiotech.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.base.BaseActivity
import com.biotech.sowbhagyabiotech.databinding.ActivityOtpBinding
import com.biotech.sowbhagyabiotech.ui.profile.ProfileActivity
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import java.util.regex.Pattern


class Otp_activity : BaseActivity() {
    private lateinit var binding: ActivityOtpBinding
    lateinit var user_id: String
    private lateinit var otpvalidation: String
    private lateinit var mobileNumber: String
    private lateinit var otpViewModel: LoginViewModel
    private lateinit var loginViewModel: LoginViewModel

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user_id = securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        mobileNumber = securedSharedPreferenceWrapper.getString(ApiConstants.EMAIL, "")

        binding.renumber.text = "Please enter the OTP sent to $mobileNumber"

//        binding.resend.setOnClickListener {
//            callResendOtpApi()
//        }

        supportActionBar?.hide()
        otpViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        addObservers()
        binding.apply {

            pinEdit4.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DEL -> {
                            pinEdit4.text?.clear()
                            pinEdit3.requestFocus()
                            true
                        }

                        else -> false
                    }
                } else false
            }
            pinEdit3.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DEL -> {
                            pinEdit3.text?.clear()
                            pinEdit2.requestFocus()
                            true
                        }

                        else -> false
                    }
                } else false
            }
            pinEdit2.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DEL -> {
                            pinEdit2.text?.clear()
                            pinEdit1.requestFocus()
                            true
                        }

                        else -> false
                    }
                } else false
            }

        }

        binding.continuelogin.setOnClickListener {

            val otptext1 = binding.pinEdit1.text.toString()
            val otptext2 = binding.pinEdit2.text.toString()
            val otptext3 = binding.pinEdit3.text.toString()
            val otptext4 = binding.pinEdit4.text.toString()

            otpvalidation = otptext1 + otptext2 + otptext3 + otptext4

            Log.e("otp", otpvalidation)
            if (otpvalidation.isEmpty()) {
                binding.pinEdit1.error = "Please Enter Valid OTP"
            } else {
                CommonMethods.showProgress(this)
                otpViewModel.callOtpApi(
                    this, user_id, mobileNumber, otpvalidation
                )

            }

        }

        binding.pinEdit1.addTextChangedListener {
            if (it?.length == 1) binding.pinEdit2.requestFocus()

        }

        binding.pinEdit2.addTextChangedListener {
            if (it?.length == 1) binding.pinEdit3.requestFocus()
        }

        binding.pinEdit3.addTextChangedListener {
            if (it?.length == 1) binding.pinEdit4.requestFocus()
        }


        binding.resendTV.setOnClickListener {
            validateAndCallSignInAPI()
        }

    }

    private fun addObservers() {


        //resend otp
        loginViewModel.loginModelResponse.observe(this) {
            CommonMethods.hideProgress()
            if (it.Status == true) {
                Toast.makeText(this, ""+it.Message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "" + it.Message, Toast.LENGTH_SHORT).show()
            }
        }

        otpViewModel.otpModelResponse.observe(this) {

            Log.e("User Login","User Login ${it.Response?.userId}")
            Log.e("User Login","User Login ${it.Response?.mobileNumber}")
            if (it.Status == true) {
                CommonMethods.hideProgress()
                if (it.code==1){
                   // Toast.makeText(this, "" + it.Message, Toast.LENGTH_SHORT).show()
                    securedSharedPreferenceWrapper.putBoolean(ApiConstants.USER_LOGGED_IN, true)
                    securedSharedPreferenceWrapper.putString(ApiConstants.USER_ID, it.Response?.userId)

                    startActivity(Intent(this, Home_Activity::class.java))
                    finish()
                }else if (it.code==2){
                    Toast.makeText(this, "wrong OTP", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                }
            } else {
                CommonMethods.hideProgress()
                Toast.makeText(this, "wrong OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun validateAndCallSignInAPI() {
        CommonMethods.showProgress(this)
        loginViewModel.callLoginApi(
            this,
            mobileNumber,
        )
    }

    override fun onResume() {
        super.onResume()

        binding.pinEdit1.setText("")
        binding.pinEdit2.setText("")
        binding.pinEdit3.setText("")
        binding.pinEdit4.setText("")

    }

    fun isValidString(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }
}

