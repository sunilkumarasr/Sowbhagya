package com.biotech.sowbhagyabiotech.authentication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.ActivityRegistrationBinding
import com.biotech.sowbhagyabiotech.ui.web_url.WebViewUrilLoad
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.ConstantUtils

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var registrationViewModel: RegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ConstantUtils.changeNotificationBarColor(
            this,
            ContextCompat.getColor(this, R.color.tab_hilet_color),
            false
        )
        supportActionBar?.hide()
        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        addObservers()
        binding.tvSignIn.setOnClickListener {
            gotoRegistration()
        }
        binding.ivClose.setOnClickListener {
            gotoRegistration()
        }
        binding.btnRegistration.setOnClickListener {

                if (binding.tieName.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Full Name")
                }
                if (binding.tiePhone.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Mobile Number")
                }
                if (binding.tieEmail.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Email")
                }
                if (binding.tieHouseNumber.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter House Number")
                }
                if (binding.tieLandMark.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Land Mark")
                }
                if (binding.tieLocality.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Locality")
                }
                if (binding.tieCity.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter City")
                }
                if (binding.tieState.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter State")
                }
                if (binding.tiePicode.text.toString().isEmpty()) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Picode")
                }
                if (binding.tiePhone.text.toString().toString().length < 10) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Valid Mobile number")
                }
                if (binding.tiePicode.text.toString().toString().length < 6) {
                    ConstantUtils.showToast(applicationContext, "Please Enter Valid Pin code")
                }
                if(!isValidEmail(binding.tieEmail.text.toString().trim())){
                    ConstantUtils.showToast(applicationContext, "Please Enter Valid Email Id")
                }


                if(binding.cbAgree.isChecked){
                    val name = binding.tieName.text.toString().trim()
                    val phoneNumber = binding.tiePhone.text.toString().trim()
                    val email = binding.tieEmail.text.toString().trim()
                    val hno = binding.tieHouseNumber.text.toString().trim()
                    val landmark = binding.tieLandMark.text.toString().trim()
                    val locality = binding.tieLocality.text.toString().trim()
                    val city = binding.tieCity.text.toString().trim()
                    val state = binding.tieState.text.toString().trim()
                    val pincode = binding.tiePicode.text.toString().trim()

                        CommonMethods.showProgress(this)
                    registrationViewModel.callRegistrationApi(
                            this,
                        phoneNumber, name, email,hno,landmark,locality,city,state,pincode
                        )

                }else{
                    ConstantUtils.showToast(applicationContext, "Please check Terms & conditions..!")
                }

        }
        binding.tvPrivacy.setOnClickListener {
            Intent(applicationContext, WebViewUrilLoad::class.java).also {
                it.putExtra("url_type", ApiConstants.PRIVACY_POLICY_TAG)
                startActivity(it)

            }
        }
        binding.tvTerms.setOnClickListener {
            Intent(applicationContext, WebViewUrilLoad::class.java).also {
                it.putExtra("url_type", ApiConstants.TERMS_CONDITIONS_TAG)
                startActivity(it)

            }
        }
    }


    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun gotoRegistration() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun addObservers() {

        registrationViewModel.registrationModelResponse.observe(this) {

            if (it.status == true) {
                CommonMethods.hideProgress()
                ConstantUtils.showSuccessToast(applicationContext, it.message.toString())
                //Toast.makeText(this, ""+it.message, Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                CommonMethods.hideProgress()
                Toast.makeText(this, "" + it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}