package com.biotech.sowbhagyabiotech.ui.orders

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.databinding.FragmentsActiveordersBinding
import com.biotech.sowbhagyabiotech.models.ActiveOrders
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.ActiveOrders_Adapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class ActiveOrdersFragment : Fragment() {

    private var _binding: FragmentsActiveordersBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    private lateinit var activeAdapter: ActiveOrders_Adapter
    var activeOrders: List<ActiveOrders> = ArrayList<ActiveOrders>()
    var cartData: List<ActiveOrders> = ArrayList<ActiveOrders>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        _binding = FragmentsActiveordersBinding.inflate(inflater, container, false)
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


        homeViewModel.changeStatusModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                homeViewModel.callmyordersListApi(
                    requireContext(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
                )
            } else {
                CommonMethods.hideProgress()
            }
        }

        return root
    }

    private fun setUpRecyclerView() {
        val ll = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        binding.activeRCID.layoutManager = ll
        activeAdapter = ActiveOrders_Adapter(
            activity as Activity, activeOrders as ArrayList<ActiveOrders>
        )

        binding.activeRCID.adapter = activeAdapter

        activeAdapter.addCategoryListener(object :
            ActiveOrders_Adapter.MoreClickEventListenerorderID {
            override fun categoryItemOnClick(orderid: String) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Alert")
                builder.setMessage("Are you sure want to cancel this order?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    homeViewModel.changeStatusApi(requireContext(), orderid)
                    CommonMethods.showProgress(requireActivity())
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
            }
        })

    }

    private fun addObservers() {

        homeViewModel.myorderslistModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")

                    activeOrders = it.Response?.activeOrders!!
                    activeAdapter.addList(it.Response?.activeOrders!!)

                    if (activeOrders.isEmpty()){
                        binding.nodata.visibility=View.VISIBLE
                        binding.activeRCID.visibility=View.GONE
                    }else{
                        binding.nodata.visibility=View.GONE
                        binding.activeRCID.visibility=View.VISIBLE
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