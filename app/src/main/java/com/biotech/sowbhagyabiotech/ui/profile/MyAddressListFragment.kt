package com.biotech.sowbhagyabiotech.ui.profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.AddresslistBinding
import com.biotech.sowbhagyabiotech.models.AddressListResponse
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.MyAddress_Adapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import java.util.ArrayList

class MyAddressListFragment : Fragment() {

    private var _binding: AddresslistBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    lateinit var address_list_adapter: MyAddress_Adapter
    var addresslist: List<AddressListResponse> = ArrayList<AddressListResponse>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = AddresslistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        addObservers()
        setUpRecyclerView()

        CommonMethods.showProgress(requireActivity())
        homeViewModel.callgetAddressListApi(
            requireActivity(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        )
        binding.fabAddLocation.setOnClickListener {
            val navController = Navigation.findNavController(
                context as Activity, R.id.nav_host_fragment_activity_home
            )
            navController.navigate(R.id.navigation_editaddadress)
        }

        return root
    }
    private fun setUpRecyclerView() {
        val ll = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        binding.addressrecycle.layoutManager = ll
        address_list_adapter = MyAddress_Adapter(activity as Home_Activity,
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
                    val navController = Navigation.findNavController(
                        context as Activity, R.id.nav_host_fragment_activity_home
                    )
                    navController.navigate(R.id.navigation_editaddadress)
                }

                override fun deleteAddress(id: String) {
                    CommonMethods.showProgress(requireActivity())
                    homeViewModel.deleteAddress(
                        requireActivity(), "",id
                    )

                }

            })

        binding.addressrecycle.adapter = address_list_adapter

    }

    private fun addObservers() {

        homeViewModel.getaddressModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                if (it.Response.isEmpty()) {
                    binding.nodata.visibility = View.VISIBLE
                    binding.addressrecycle.visibility = View.GONE
                    binding.fabAddLocation.visibility = View.VISIBLE
                } else {
                    address_list_adapter.addList(it.Response)
                    addresslist =it.Response
                    binding.nodata.visibility = View.GONE
                    binding.addressrecycle.visibility = View.VISIBLE

                    binding.fabAddLocation.visibility = View.GONE
                }

            } else {
                CommonMethods.hideProgress()

            }
        }

        homeViewModel.addressModelResponse.observe(requireActivity()){
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
                requireActivity(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
            )
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("Address List")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }
}