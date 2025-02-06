package com.biotech.sowbhagyabiotech.ui.wishlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.databinding.FragmentWishlistBinding
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.WishListAdapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class WishListFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var wishListsAdapter: WishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        val view = binding.root

        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        addObservers()
        setUpRecyclerView()

        CommonMethods.showProgress(requireActivity())
        homeViewModel.callgetWihListApi(
            requireContext(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        )

        return view
    }

    private fun setUpRecyclerView() {
        val ll = GridLayoutManager(
            requireActivity(), 2
        )
        binding.wishlistRecyclerView.layoutManager = ll
        wishListsAdapter = WishListAdapter()
        binding.wishlistRecyclerView.adapter = wishListsAdapter


    }


    private fun addObservers() {

        homeViewModel.getwishlistModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                if (it.Response.isEmpty()){
                    binding.nodata.visibility=View.VISIBLE
                    binding.wishlistRecyclerView.visibility=View.GONE
                }else{
                    binding.nodata.visibility=View.GONE
                    binding.wishlistRecyclerView.visibility=View.VISIBLE
                    wishListsAdapter.addList(it.Response)

                }

            } else {
                CommonMethods.hideProgress()

            }
        }

    }


    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("WishList")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }
}