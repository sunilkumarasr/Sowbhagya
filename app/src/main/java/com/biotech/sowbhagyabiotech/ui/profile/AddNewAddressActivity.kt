package com.biotech.sowbhagyabiotech.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.ActivityAddNewAddressBinding
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.ConstantUtils

class AddNewAddressActivity : AppCompatActivity() {

    private var _binding: ActivityAddNewAddressBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    lateinit var fullname: String
    lateinit var number: String
    lateinit var hno: String
    lateinit var landmark: String
    lateinit var locality: String
    lateinit var city: String
    lateinit var state: String
    lateinit var pincode: String

    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddNewAddressBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ConstantUtils.changeNotificationBarColor(
            this,
            ContextCompat.getColor(this, R.color.tab_hilet_color),
            false
        )
        supportActionBar?.hide()

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this@AddNewAddressActivity, "sowbhagya")

        addObservers()

        binding.updateadress.setOnClickListener {
            fullname = binding.fullnameEdit.text.toString()
            number = binding.mobileEdit.text.toString()
            val email = binding.emailEdit.text.toString()
            hno = binding.hnoEdit.text.toString()
            landmark = binding.landmarkEdit.text.toString()
            locality = binding.localityEdit.text.toString()
            city = binding.cityEdit.text.toString()
            state = binding.stateEdit.text.toString()
            pincode = binding.pincodeEdit.text.toString()
            if (fullname.isEmpty()) {
                binding.fullnameEdit.error = "Please Enter Name"
            } else if (number.isEmpty() || (number.length < 10) || (number.length > 10)) {
                binding.mobileEdit.error = "Please Enter Mobile Number"
            } /*else if (email.isEmpty()) {
                binding.emailEdit.error = "Please Enter Email Address"
            }*/ else if (hno.isEmpty()) {
                binding.hnoEdit.error = "Please Enter house Number"
            } else if (landmark.isEmpty()) {
                binding.landmarkEdit.error = "Please Enter Landmark"
            } else if (locality.isEmpty()) {
                binding.localityEdit.error = "Please Enter Locality"
            } else if (city.isEmpty()) {
                binding.cityEdit.error = "Please Enter City"
            } else if (state.isEmpty()) {
                binding.stateEdit.error = "Please Enter State"
            } else if (pincode.isEmpty()) {
                binding.pincodeEdit.error = "Please Enter Pincode"
            } else {
                homeViewModel.callAdduseraddressApi(
                    this@AddNewAddressActivity,
                    securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, ""),
                    fullname,
                    email_id = email,
                    mobile_number = number,
                    pincode = pincode,
                    address = hno,
                    apartment = landmark,
                    street_address = locality,
                    city = city,
                    state = state,
                    country = "India"
                )
            }
        }


        binding.ivClose.setOnClickListener {
            finish()
        }

    }

    private fun addObservers() {
        homeViewModel.addAddressModelResponse.observe(this@AddNewAddressActivity) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                finish()
                Toast.makeText(
                    this@AddNewAddressActivity, "Address updated successfully...", Toast.LENGTH_LONG
                ).show()
            } else {
                CommonMethods.hideProgress()
            }
        }

    }

}