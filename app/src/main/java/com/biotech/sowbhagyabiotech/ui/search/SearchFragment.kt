package com.biotech.sowbhagyabiotech.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.databinding.FragmentsearchScreenBinding
import com.biotech.sowbhagyabiotech.models.SubCatProductsResponseModel
import com.biotech.sowbhagyabiotech.roomdb.AppRoomDataBase
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.SearchPropertiesAdapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.ui.product.ProducatDetailsActivity
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.Utilities
import kotlin.math.roundToLong

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentsearchScreenBinding

    private lateinit var searchAdapter: SearchPropertiesAdapter
    var cartData: List<CartItems> = ArrayList<CartItems>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    private lateinit var homeViewModel: HomeViewModel
    private var temporaryList: ArrayList<SubCatProductsResponseModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentsearchScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        this.homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val appRoomDataBase: AppRoomDataBase by lazy {
            AppRoomDataBase.getInstance(requireActivity())
        }
        appRoomDataBase.cropTableDao()
        val cartVM = CartViewModel(activity, false)
        cartVM.cartData()
        cartVM.getCartItems.observe(requireActivity()) {
                cartItems -> cartData = cartItems
            if(searchAdapter!=null)
            {
                searchAdapter.setCartData(cartData)
            }
        }
        val viewModel = CartViewModel(activity as Activity)
        viewModel.cartCount()

        addObservers()
        setUpRecyclerView()
        CommonMethods.showProgress(activity as Home_Activity)
        homeViewModel.callsearchproductListApi(
            activity as Home_Activity, "1", ""
        )

        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {

                try {

                    searchAdapter.filter.filter(charSequence.toString())

                    binding.searchfilterRV.visibility = View.VISIBLE
                    binding.searchedRV.visibility = View.GONE
                } catch (e: java.lang.NullPointerException) {
                    e.printStackTrace()
                } catch (e: UninitializedPropertyAccessException) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                //after the change calling the method and passing the search input


                try {


                    editable?.toString()?.let { Log.e("newtext", it) }

                    searchAdapter.filter.filter(editable.toString())

                    homeViewModel.callsearchproductListApi(
                        activity as Home_Activity, "1", editable.toString()
                    )
                    binding.searchfilterRV.visibility = View.VISIBLE
                    binding.searchedRV.visibility = View.GONE
                } catch (e: java.lang.NullPointerException) {
                    e.printStackTrace()
                } catch (e: UninitializedPropertyAccessException) {
                    e.printStackTrace()
                }
            }
        })
        return root
    }

    private fun setUpRecyclerView() {
        val ll = GridLayoutManager(
            requireActivity(), 2
        )
        binding.searchedRV.layoutManager = ll
        searchAdapter =
            SearchPropertiesAdapter(
                cartData as ArrayList<CartItems>,
                object : SearchPropertiesAdapter.MoreClickEventListener {
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
                        Log.e("stock", "" + languageList.productNum +" cartQty "+cartQty)
                        val viewModel = CartViewModel(activity)
                        if(cartQty=="0")
                            viewModel.deleteProduct(items.product_id)
                        else
                        viewModel.insert(items)
                       /* Toast.makeText(
                            activity,
                            "Item added to cart successfully",
                            Toast.LENGTH_LONG
                        )
                            .show()*/
                    }

                })

        binding.searchedRV.adapter = searchAdapter

        val ll1 = GridLayoutManager(
            requireActivity(), 1
        )
        binding.searchfilterRV.layoutManager = ll1
        searchAdapter =
            SearchPropertiesAdapter(
                cartData  as ArrayList<CartItems>,
                object : SearchPropertiesAdapter.MoreClickEventListener {
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
                        Log.e("stock", "" + languageList.productNum +" cartQty "+cartQty)
                        val viewModel = CartViewModel(activity)
                        if(cartQty=="0")
                            viewModel.deleteProduct(items.product_id)
                        else
                        viewModel.insert(items)
                      /*  Toast.makeText(
                            activity,
                            "Item added to cart successfully",
                            Toast.LENGTH_LONG
                        )
                            .show()*/
                    }

                })

        binding.searchfilterRV.adapter = searchAdapter


    }


    private fun addObservers() {

        homeViewModel.subsearchproductModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")

                binding.searchfilterRV.adapter = searchAdapter
                temporaryList = it.Response
                if (temporaryList.isNullOrEmpty()) {
                    binding.searchfilterRV.visibility = View.GONE
                    binding.noDataFoundTV.visibility = View.VISIBLE
                } else {
                    binding.searchfilterRV.visibility = View.VISIBLE
                    binding.noDataFoundTV.visibility = View.GONE
                }
                searchAdapter.setData(temporaryList)

            } else {
                CommonMethods.hideProgress()

            }
        }

    }


    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("Search")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }
}