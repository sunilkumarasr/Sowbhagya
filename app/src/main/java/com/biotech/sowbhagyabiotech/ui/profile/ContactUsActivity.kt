package com.biotech.sowbhagyabiotech.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.authentication.LoginActivity
import com.biotech.sowbhagyabiotech.databinding.ActivityContactUsBinding
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.ConstantUtils

class ContactUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactUsBinding
    lateinit var contactUsViewModel: ContactUsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ConstantUtils.changeNotificationBarColor(
            this,
            ContextCompat.getColor(this, R.color.color_primary),
            false
        )
        contactUsViewModel = ViewModelProvider(this)[ContactUsViewModel::class.java]
        addObservers()
        binding.btnContactUs.setOnClickListener {
            if (validateFields()) {
                val name = binding.tieName.text.toString().trim()
                val phoneNumber = binding.tiePhone.text.toString().trim()
                val email = binding.tieEmail.text.toString().trim()
                val subject = binding.tieSubject.text.toString().trim()
                val message = binding.tieMessage.text.toString().trim()


                CommonMethods.showProgress(this)
                contactUsViewModel.callNotificationsApi(
                    this,
                    phoneNumber, name, email,subject, message
                )
            }
        }
    }

    private fun validateFields(): Boolean {

        var ret = true
        if (!ConstantUtils.hasEditText(binding.tieName, "Please Enter Full Name")) ret = false

        if (!ConstantUtils.hasEditText(binding.tiePhone, "Please Mobile Number")) ret = false
        if (binding.tiePhone.text.toString().toString().length < 10) {
            /*if (ConstantUtils.hasEditText(
                    binding.tiePhone,
                    "Please Enter Valid Mobile number"
                )
            ) ret = false*/
            binding.tiePhone.setError("Please Enter Valid Mobile number")
            ret = false
        }

        if (!ConstantUtils.hasEditText(binding.tieEmail, "Please Enter Email Id")) ret = false
        if (!isValidEmail(binding.tieEmail.text.toString().trim())) {
            binding.tieEmail.setError("Please Enter Valid Email Id")
            ret = false

        }

        if (!ConstantUtils.hasEditText(binding.tieSubject, "Please Enter Subject")) ret = false
        if (!ConstantUtils.hasEditText(binding.tieMessage, "Please Enter Message")) ret = false


        return ret

    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun gotoRegistration() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun addObservers() {

        contactUsViewModel.contactUsModel.observe(this) {

            if (it.Status == true) {
                CommonMethods.hideProgress()
                ConstantUtils.showToast(applicationContext, it.Message.toString())
                //Toast.makeText(this, ""+it.message, Toast.LENGTH_SHORT).show()


                finish()
            } else {
                CommonMethods.hideProgress()
                Toast.makeText(this, "" + it.Message.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }
}