package com.biotech.sowbhagyabiotech.ui.profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.EditadresssScreenBinding
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class EditAddressFragment : Fragment() {

    private var _binding: EditadresssScreenBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    lateinit var fullname: String
    lateinit var number: String
    lateinit var hno: String
    lateinit var landmark: String
    lateinit var locality: String
    lateinit var city: String
    lateinit var state: String
    lateinit var pincode: String
    lateinit var address_id: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = EditadresssScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        addObservers()
        val fullnames = securedSharedPreferenceWrapper.getString("fullname", "").toString()
        val mnum = securedSharedPreferenceWrapper.getString("mnum", "").toString()
        val hn = securedSharedPreferenceWrapper.getString("hn", "").toString()
        val lmark = securedSharedPreferenceWrapper.getString("lmark", "").toString()
        val loc = securedSharedPreferenceWrapper.getString("loc", "").toString()
        val cit = securedSharedPreferenceWrapper.getString("cit", "").toString()
        val stat = securedSharedPreferenceWrapper.getString("stat", "").toString()
        val pin = securedSharedPreferenceWrapper.getString("pin", "").toString()
        address_id = securedSharedPreferenceWrapper.getString("address_id", "").toString()
        if(fullnames.length < 0){
            binding.actUpdate.text = "Create"
        }else{
            binding.actUpdate.text = "Update"
        }

        binding.fullnameEdit.setText(fullnames)
        binding.mobileEdit.setText(mnum)
        binding.hnoEdit.setText(hn)
        binding.landmarkEdit.setText(lmark)
        binding.localityEdit.setText(loc)
        binding.cityEdit.setText(cit)
        binding.stateEdit.setText(stat)
        binding.pincodeEdit.setText(pin)
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
                if(address_id=="-1")
                    address_id=""

                homeViewModel.calluseraddressApi(
                    requireActivity(),
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
                    country = "India",
                    address_id=address_id
                )

            }

        }



        return root
    }


    private fun addObservers() {

        homeViewModel.addressModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                val navController = Navigation.findNavController(
                    context as Activity, R.id.nav_host_fragment_activity_home
                )
                navController.navigate(R.id.navigation_getadresslist)
                Toast.makeText(
                    activity, "Address updated successfully...", Toast.LENGTH_LONG
                ).show()
            } else {
                CommonMethods.hideProgress()

            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("All Categories")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }
}