package com.biotech.sowbhagyabiotech.ui.cart

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.AddresslistBinding
import com.biotech.sowbhagyabiotech.models.AddressListResponse
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.DefaultAddress_Adapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class UserAddressListFragment : Fragment() {

    private var _binding: AddresslistBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

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

        CommonMethods.showProgress(requireActivity())
        homeViewModel.callgetAddressListApi(
            requireActivity(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        )
        binding.fabAddLocation.setOnClickListener {
            val navController = Navigation.findNavController(
                context as Activity, R.id.nav_host_fragment_activity_home
            )
            navController.navigate(R.id.navigation_addadress)
        }


        return root
    }

    private fun addObservers() {

        homeViewModel.getaddressModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                if (it.Response!!.isEmpty()) {

                    val navController = Navigation.findNavController(
                        context as Activity, R.id.nav_host_fragment_activity_home
                    )
                    navController.navigate(R.id.navigation_addadress)

                } else {

                    Log.e("checkresp", "" + it.Response)
                    try {

                        binding.nodata.visibility = View.GONE
                        binding.addressrecycle.visibility = View.VISIBLE
                        var address_list_adapter = DefaultAddress_Adapter(
                            activity as Activity, it.Response as ArrayList<AddressListResponse>
                        )

                        // at last set adapter to recycler view.
                        binding.addressrecycle.adapter = address_list_adapter

                        address_list_adapter.notifyDataSetChanged()

                    } catch (e: java.lang.NullPointerException) {
                        e.printStackTrace()
                    } catch (e1: IllegalStateException) {
                        e1.printStackTrace()

                    }


                }

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
        (activity as Home_Activity).setHeaderTitleVisible("Address List")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }
}