package com.biotech.sowbhagyabiotech.ui.orders

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.databinding.FragmentsPastordersBinding
import com.biotech.sowbhagyabiotech.models.PastOrders
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.PastOrders_Adapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class PastOrdersFragment : Fragment() {

    private var _binding: FragmentsPastordersBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    private lateinit var pastAdapter: PastOrders_Adapter
    var pastOrders: List<PastOrders> = ArrayList<PastOrders>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        _binding = FragmentsPastordersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        addObservers()
        setUpRecyclerView()

        CommonMethods.showProgress(requireActivity())
        homeViewModel.callmyordersListApi(
            requireContext(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        )
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            homeViewModel.callmyordersListApi(
                requireContext(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
            )
            binding.swipeRefresh.isRefreshing = false
        }


        return root
    }

    private fun setUpRecyclerView() {
        val ll = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        binding.productsRCID.layoutManager = ll
        pastAdapter = PastOrders_Adapter(
            activity as Activity, pastOrders as ArrayList<PastOrders>
        )
        binding.productsRCID.adapter = pastAdapter


    }

    private fun addObservers() {

        homeViewModel.myorderslistModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                if (it.Response?.pastOrders!!.isEmpty()) {
                    binding.nodata.visibility = View.VISIBLE
                    binding.productsRCID.visibility = View.GONE
                } else {
                    binding.nodata.visibility = View.GONE
                    binding.productsRCID.visibility = View.VISIBLE
                    pastOrders = it.Response?.pastOrders!!
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
}