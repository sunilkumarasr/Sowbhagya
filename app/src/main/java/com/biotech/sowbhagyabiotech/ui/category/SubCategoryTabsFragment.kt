package com.biotech.sowbhagyabiotech.ui.category

import android.app.Activity
import android.content.Intent
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
import com.biotech.sowbhagyabiotech.base.BaseApplication
import com.biotech.sowbhagyabiotech.databinding.FragmentSubcategoryTabsBinding
import com.biotech.sowbhagyabiotech.models.SubCatProductsResponseModel
import com.biotech.sowbhagyabiotech.models.SubCategoriesResponseModel
import com.biotech.sowbhagyabiotech.roomdb.AppRoomDataBase
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.SubCatTabListAdapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.ui.product.ProducatDetailsActivity
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.Utilities
import kotlin.math.roundToLong


class SubCategoryTabsFragment : Fragment() {
    private lateinit var binding: FragmentSubcategoryTabsBinding
    private var mCategoryResponse = SubCategoriesResponseModel()
    private lateinit var subcatListsAdapter: SubCatTabListAdapter
    private lateinit var homeViewModel: HomeViewModel
    var cartData: List<CartItems> = ArrayList<CartItems>()
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    lateinit var baseApplication: BaseApplication
    var catId :String? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubcategoryTabsBinding.inflate(layoutInflater)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        baseApplication = BaseApplication()
        val appRoomDataBase: AppRoomDataBase by lazy {
            AppRoomDataBase.getInstance(requireActivity())
        }
        appRoomDataBase.cropTableDao()
        val cartVM = CartViewModel(activity, false)
        cartVM.cartData()
        cartVM.getCartItems.observe(requireActivity()) { cartItems -> cartData = cartItems }
        val viewModel = CartViewModel(activity as Activity)
        viewModel.cartCount()

        addObservers()
        setUpRecyclerView()
        CommonMethods.showProgress(requireActivity())

        catId?.let {
            homeViewModel.callsubcatproductListApi(
               activity as Home_Activity, it
            )
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun addObservers() {

        homeViewModel.subcatproductModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")

                if (it.Response.isEmpty()){
                    binding.nodata.visibility=View.VISIBLE
                    binding.subcategoriesRecyclerView.visibility=View.GONE
                }else{

                    binding.nodata.visibility=View.GONE
                    binding.subcategoriesRecyclerView.visibility=View.VISIBLE
                    subcatListsAdapter.addList(it.Response)

                }

            } else {
                CommonMethods.hideProgress()

            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(categoryResponse: SubCategoriesResponseModel) =
            SubCategoryTabsFragment().apply {
                mCategoryResponse = categoryResponse
                 catId =mCategoryResponse.subCategoriesId

            }

    }

    private fun setUpRecyclerView() {
        val ll = GridLayoutManager(
            requireActivity(), 1
        )
        binding.subcategoriesRecyclerView.layoutManager = ll
        subcatListsAdapter =
            SubCatTabListAdapter(cartData, object : SubCatTabListAdapter.MoreClickEventListener {
                override fun onProductDetailsClicked(productId: String?) {
                    if (productId != null) {
                        val bundle = Bundle()
                        bundle.putString("SELECTED_PRODUCT_ID", productId)
                        /*findNavController().navigate(
                            R.id.navigation_product_details, bundle
                        )*/

                        securedSharedPreferenceWrapper.putString("SELECTED_PRODUCT_ID", productId)
                        val intent = Intent(requireContext(), ProducatDetailsActivity::class.java)
                        startActivity(intent)
                        //resultLauncher.launch(intent)
                        requireActivity().finish()
                    }
                }

                override fun onAddToCartClicked(
                    languageList: SubCatProductsResponseModel?, cartQty: String?
                ) {

                    val gstAmountCal: String = (((languageList?.tax_percentage?.toDouble() ?: 0.0) * (languageList?.offerPrice?.toDouble()?:0.0))/100).roundToLong().toString()
                    val items = CartItems(
                        0,
                        "0",
                        Utilities.getDeviceID(activity),
                        ApiConstants.PRODUCTS_CART,
                        languageList!!.productName,
                        languageList.productImage,
                        cartQty,
                        java.lang.String.valueOf(languageList.offerPrice),
                        java.lang.String.valueOf(languageList.offerPrice),
                        languageList.productsId.toString(),
                        languageList.productNum,
                        languageList.tax_percentage,
                        gstAmountCal
                    )
                    Log.e("stock", "" + languageList.productNum)
                    val viewModel = CartViewModel(activity)
                    viewModel.insert(items)
                   // Toast.makeText(activity, "Item added to cart successfully", Toast.LENGTH_LONG).show()
                }

            })
        binding.subcategoriesRecyclerView.adapter = subcatListsAdapter

    }

    /*
        override fun onAddToCartClicked(itemsData: SubCatProductsResponseModel?, cartQty: String?) {
            val items = CartItems(
                0,
                "0",
                Utilities.getDeviceID(activity),
                ApiConstants.PRODUCTS_CART,
                itemsData!!.productName,
                itemsData.productImage,
                cartQty,
                java.lang.String.valueOf(itemsData.offerPrice),
                java.lang.String.valueOf(itemsData.offerPrice),
                itemsData.productsId.toString(),
                itemsData.productNum
            )
            Log.e("stock", "" + itemsData.productNum)
            val viewModel = CartViewModel(activity)
            viewModel.insert(items)
            Toast.makeText(activity, "Item added to cart successfully", Toast.LENGTH_LONG).show()
        }
    */

}