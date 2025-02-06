package com.biotech.sowbhagyabiotech.ui.category

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.FragmentCategoriesBinding
import com.biotech.sowbhagyabiotech.models.CategoriesResponseModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.CategoriesListAdapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var categoriesAdapter: CategoriesListAdapter
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        addObservers()
        setUpRecyclerView()

        CommonMethods.showProgress(requireActivity())
        homeViewModel.callcategoriesListApi(
            requireContext()
        )

        return root
    }

    private fun setUpRecyclerView() {
        val ll = GridLayoutManager(
            requireActivity(), 3
        )
        binding.categoriesRecyclerView.layoutManager = ll
        categoriesAdapter = CategoriesListAdapter()
        binding.categoriesRecyclerView.adapter = categoriesAdapter

        categoriesAdapter.addCategoryListener(object :
            CategoriesListAdapter.MoreClickEventListener {
            override fun categoryItemOnClick(item: CategoriesResponseModel) {
                val navController =
                    Navigation.findNavController(
                        activity as Activity,
                        R.id.nav_host_fragment_activity_home
                    )
                val bundle = Bundle()
                bundle.putString(ApiConstants.NEWS_BUNDLE, item.categoriesId)
                securedSharedPreferenceWrapper.putString("CategoryId", item.categoriesId)
                navController.navigate(R.id.navigation_subcategory)
            }

        })


    }

    private fun addObservers() {

        homeViewModel.categoryModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                categoriesAdapter.addList(it.Response)

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