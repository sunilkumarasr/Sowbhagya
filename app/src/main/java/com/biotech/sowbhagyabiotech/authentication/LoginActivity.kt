package com.biotech.sowbhagyabiotech.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.base.BaseActivity
import com.biotech.sowbhagyabiotech.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        addObservers()

        binding.continuelogin.setOnClickListener {
            it.hideKeyboard()
            validateAndCallSignInAPI()
        }

        binding.tvRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }

    }

    fun View.hideKeyboard() =
        ViewCompat.getWindowInsetsController(this)?.hide(WindowInsetsCompat.Type.ime())

    private fun validateAndCallSignInAPI() {
        val userName = binding.mobileNumberET.text.toString().trim()
        /*if (validateSignIn(userName)) {
            CommonMethods.showProgress(this)
            loginViewModel.callLoginApi(
                this,
                binding.mobileNumberET.text?.trim().toString(),
            )
        }*/
        if (userName.isEmailValid()) {
            CommonMethods.showProgress(this)
            loginViewModel.callLoginApi(
                this,
                userName,
            )
        }else{
            Toast.makeText(
                this, "Please Enter Email", Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun validateSignIn(userName: String): Boolean {
        var validCount = 0
        if (userName.isEmpty()) {
            Toast.makeText(
                this, resources.getString(R.string.enter_your_mobile_number), Toast.LENGTH_SHORT
            ).show()
            validCount = +1
        }
        return validCount == 0
    }


    private fun validateRegister(
        mobilenum: String,

        ): Boolean {

        var validCount = 0

        if (mobilenum.isEmpty()) {

            Toast.makeText(
                this, resources.getString(R.string.enter_your_mobile_number), Toast.LENGTH_SHORT
            ).show()
            validCount = +1
        }
        return validCount == 0
    }


    private fun addObservers() {

        loginViewModel.loginModelResponse.observe(this) {

            if (it.Status == true) {
                CommonMethods.hideProgress()
                Toast.makeText(this, ""+it.Message, Toast.LENGTH_SHORT).show()

                securedSharedPreferenceWrapper.putString(ApiConstants.USER_ID, it.Response?.userId)

                securedSharedPreferenceWrapper.putString(
                    ApiConstants.Mobile_NUM, it.Response?.mobileNumber
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.EMAIL, it.Response?.email_id
                )

                startActivity(Intent(this, Otp_activity::class.java))
                finish()
            } else {
                CommonMethods.hideProgress()
                Toast.makeText(this, "" + it.Message, Toast.LENGTH_SHORT).show()
            }

        }

    }


    private fun clearTextFields() {
        binding.mobileNumberET.setText("")


    }

}