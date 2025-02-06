package com.biotech.sowbhagyabiotech.ui.category

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.databinding.FragmentSubcategoriesBinding
import com.biotech.sowbhagyabiotech.models.SubCategoriesResponseModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.AdapterViewPager
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods


class SubCategoryFragment : Fragment() {
    private var arrayListOfsubCategory = ArrayList<SubCategoriesResponseModel>()
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private var subcategoryName: String = ""
    private var catId: String = ""
    private var bundleData: SubCategoriesResponseModel = SubCategoriesResponseModel()

    private var _binding: FragmentSubcategoriesBinding? = null
    private var isBundle = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSubcategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        val bundle = Bundle()
        catId = securedSharedPreferenceWrapper.getString("CategoryId","")
        addObservers()

        if (arrayListOfsubCategory.isEmpty()) {
            CommonMethods.showProgress(requireActivity())

            homeViewModel.callsubcategoriesListApi(
                requireActivity(), catId
            )
            Log.e(TAG, "onCreateView: $catId")


        } else {

            setupTabsLayout()
        }

        return root
    }


    private fun setupTabsLayout() {


        var fragmentList = ArrayList<Fragment>()

        arrayListOfsubCategory.forEachIndexed { index, categoryResponse ->
            fragmentList.add(index, SubCategoryTabsFragment.newInstance(categoryResponse))
        }


        val adapter = AdapterViewPager(
            fragmentList, requireActivity().supportFragmentManager, lifecycle
        )

        binding.viewPagerTeam.adapter = adapter

        TabLayoutMediator(binding.tabLayoutTeam, binding.viewPagerTeam) { tab, position ->
            tab.text = arrayListOfsubCategory[position].subCategoryName

        }.attach()



        binding.tabLayoutTeam.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })


    }


    private fun addObservers() {

        homeViewModel.subcatModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                arrayListOfsubCategory = it.Response
                if (it.Response.isEmpty()){
                    binding.nodata.visibility=View.VISIBLE
                    binding.viewPagerTeam.visibility=View.GONE
                }else{

                    binding.nodata.visibility=View.GONE
                    binding.viewPagerTeam.visibility=View.VISIBLE
                    setupTabsLayout()

                }

            } else {
                CommonMethods.hideProgress()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("Categories")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}