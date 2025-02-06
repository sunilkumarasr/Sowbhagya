package com.biotech.sowbhagyabiotech.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.ActivityMyAddressListBinding
import com.biotech.sowbhagyabiotech.models.AddressListResponse
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.MyAddress_Adapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.ConstantUtils
import java.util.ArrayList

class MyAddressActivity : AppCompatActivity() {

    private var _binding: ActivityMyAddressListBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    lateinit var address_list_adapter: MyAddress_Adapter
    var addresslist: List<AddressListResponse> = ArrayList<AddressListResponse>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyAddressListBinding.inflate(layoutInflater)
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
            SecuredSharedPreferenceWrapper(this@MyAddressActivity, "sowbhagya")

        addObservers()
        setUpRecyclerView()

        CommonMethods.showProgress(this@MyAddressActivity)
        homeViewModel.callgetAddressListApi(
            this@MyAddressActivity, securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        )
        binding.fabAddLocation.setOnClickListener {
            startActivity(Intent(this@MyAddressActivity, AddNewAddressActivity::class.java))
        }

        binding.ivClose.setOnClickListener {
            finish()
        }

    }

    private fun setUpRecyclerView() {
        val ll = LinearLayoutManager(
            this@MyAddressActivity, LinearLayoutManager.VERTICAL, false
        )
        binding.addressrecycle.layoutManager = ll
        address_list_adapter = MyAddress_Adapter(this@MyAddressActivity,
            addresslist as ArrayList<AddressListResponse>,
            object : MyAddress_Adapter.MoreClickEventListener {

                override fun categoryItemOnClick(item: AddressListResponse) {

                    securedSharedPreferenceWrapper.putString(
                        "fullname",
                        item.fullName
                    )
                    securedSharedPreferenceWrapper.putString(
                        "mnum",
                        item.mobileNumber
                    )
                    securedSharedPreferenceWrapper.putString("hn", item.apartment)
                    securedSharedPreferenceWrapper.putString(
                        "lmark",
                        item.streetAddress
                    )
                    securedSharedPreferenceWrapper.putString("loc", item.address)
                    securedSharedPreferenceWrapper.putString("cit", item.city)
                    securedSharedPreferenceWrapper.putString("stat", item.state)
                    securedSharedPreferenceWrapper.putString("pin", item.pincode)
                    securedSharedPreferenceWrapper.putString("address_id", item.addressId)
                    securedSharedPreferenceWrapper.putInt(
                        "id",
                        item.addressId!!.toInt()
                    )
                    startActivity(Intent(this@MyAddressActivity, EditAddressActivity::class.java))

                }
                override fun deleteAddress(id: String) {
                    CommonMethods.showProgress(this@MyAddressActivity)
                    homeViewModel.deleteAddress(
                        this@MyAddressActivity, "",id
                    )

                }

            })

        binding.addressrecycle.adapter = address_list_adapter

    }

    private fun addObservers() {

        homeViewModel.getaddressModelResponse.observe(this@MyAddressActivity) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                if (it.Response.isEmpty()) {
                    binding.nodata.visibility = View.VISIBLE
                    binding.addressrecycle.visibility = View.GONE
                } else {
                    address_list_adapter.addList(it.Response)
                    addresslist =it.Response
                    binding.nodata.visibility = View.GONE
                    binding.addressrecycle.visibility = View.VISIBLE
                }

            } else {
                CommonMethods.hideProgress()

            }
        }

        homeViewModel.addressModelResponse.observe(this@MyAddressActivity){
            CommonMethods.hideProgress()
            securedSharedPreferenceWrapper.putString(
                "fullname",
                ""
            )
            securedSharedPreferenceWrapper.putString(
                "mnum",
                ""
            )
            securedSharedPreferenceWrapper.putString("hn", "")
            securedSharedPreferenceWrapper.putString(
                "lmark",
                ""
            )
            securedSharedPreferenceWrapper.putString("loc","")
            securedSharedPreferenceWrapper.putString("cit", "")
            securedSharedPreferenceWrapper.putString("stat", "")
            securedSharedPreferenceWrapper.putString("pin", "")
            securedSharedPreferenceWrapper.putString("address_id", "")
            securedSharedPreferenceWrapper.putInt(
                "id",
                -1
            )
            homeViewModel.callgetAddressListApi(
                this@MyAddressActivity, securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
            )
        }

    }

}