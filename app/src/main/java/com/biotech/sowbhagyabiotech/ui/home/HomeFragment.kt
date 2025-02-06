package com.biotech.sowbhagyabiotech.ui.home

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.FragmentHomeBinding
import com.biotech.sowbhagyabiotech.models.BannersResponseModel
import com.biotech.sowbhagyabiotech.models.CategoriesResponseModel
import com.biotech.sowbhagyabiotech.models.SubCatProductsResponseModel
import com.biotech.sowbhagyabiotech.roomdb.AppRoomDataBase
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.CategoriesListAdapter
import com.biotech.sowbhagyabiotech.ui.adapter.FeaturesListAdapter
import com.biotech.sowbhagyabiotech.ui.adapter.ImageViewPagerAdapter
import com.biotech.sowbhagyabiotech.ui.adapter.SubCatListAdapter
import com.biotech.sowbhagyabiotech.ui.product.ProducatDetailsActivity
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.Utilities
import kotlin.math.roundToLong

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.fv
    private val binding get() = _binding!!

    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var categoriesAdapter: CategoriesListAdapter
    private lateinit var subcatListAdapter: SubCatListAdapter
    private lateinit var featureListAdapter: FeaturesListAdapter
    var cartData: List<CartItems> = ArrayList<CartItems>()

    //slider
    private lateinit var dotsLayout: LinearLayout
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter
    var layoutManager: LinearLayoutManager? = null
    var positionss = 0
    private var isAutoScrolling = true
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        //set text
        _binding!!.txtFeaturedProducts.setPaintFlags(_binding!!.txtFeaturedProducts.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        _binding!!.txtBestSellerProducts.setPaintFlags(_binding!!.txtBestSellerProducts.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        val appRoomDataBase: AppRoomDataBase by lazy {
            AppRoomDataBase.getInstance(requireActivity())
        }
        appRoomDataBase.cropTableDao()
        val cartVM = CartViewModel(activity, false)
        cartVM.cartData()
        cartVM.getCartItems.observe(requireActivity()) { cartItems ->
            cartData = cartItems
            if (subcatListAdapter != null) {
                subcatListAdapter.setCartData(cartData)
            }
        }
        val viewModel = CartViewModel(activity as Activity)
        viewModel.cartCount()

        addObservers()
        setUpRecyclerView()

        CommonMethods.showProgress(requireContext())

        binding.viewallTV.setOnClickListener {
            findNavController().navigate(R.id.navigation_category)
        }

        //api call
        homeViewModel.allbanners(requireContext())
        homeViewModel.callcategoriesListApi(requireContext())
        homeViewModel.callbestproductListApi(activity as Home_Activity, "best")
        homeViewModel.callfeaturedroductListApi(activity as Home_Activity, "featured")

        /*
                baseApplication.applicationScope.launch {

                    baseApplication.appRoomDataBase.cropTableDao()
                    val cartVM = CartViewModel(activity, false)
                    cartVM.cartData()
                    cartVM.getCartItems.observe(requireActivity()) { cartItems -> cartData = cartItems }
                    val viewModel = CartViewModel(activity as Activity)
                    viewModel.cartCount()

                }
        */

        return root
    }

    private fun setUpRecyclerView() {
        val ll = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        //banners
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.sliderRecyckerview.setLayoutManager(layoutManager)


        //category
        binding.homecategoriesRecyclerView.layoutManager = ll
        categoriesAdapter = CategoriesListAdapter()
        binding.homecategoriesRecyclerView.adapter = categoriesAdapter

        categoriesAdapter.addCategoryListener(object :
            CategoriesListAdapter.MoreClickEventListener {
            override fun categoryItemOnClick(item: CategoriesResponseModel) {

                val bundle = Bundle()
                bundle.putString("CategoryId", item.categoriesId)
                findNavController().navigate(
                    R.id.navigation_subcategory, bundle
                )

                securedSharedPreferenceWrapper.putString("CategoryId", item.categoriesId)
                Log.e(TAG, "categoryItemOnClick: ${item.categoriesId}")

            }

        })
        //subcat list
        val ll1 = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.bestRecyclerView.layoutManager = ll1
        subcatListAdapter =
            SubCatListAdapter(
                cartData as ArrayList<CartItems>,
                object : SubCatListAdapter.MoreClickEventListener {
                    override fun onAddToCartClicked(
                        languageList: SubCatProductsResponseModel?, cartQty: String?
                    ) {
                        val gstAmountCal: String = (((languageList?.tax_percentage?.toDouble()
                            ?: 0.0) * (languageList?.offerPrice?.toDouble()
                            ?: 0.0)) / 100).roundToLong().toString()

                        val items = CartItems(
                            0,
                            "0",
                            Utilities.getDeviceID(activity),
                            ApiConstants.PRODUCTS_CART,
                            languageList!!.productName,
                            languageList.productImage,
                            cartQty,
                            java.lang.String.valueOf(languageList.salesPrice),
                            java.lang.String.valueOf(languageList.offerPrice),
                            languageList.productsId.toString(),
                            languageList.productNum,
                            languageList.tax_percentage,
                            gstAmountCal
                        )
                        Log.e("stock", "" + languageList.productNum)
                        val viewModel = CartViewModel(activity)
                        if (cartQty == "0")
                            viewModel.deleteProduct(items.product_id)
                        else
                            viewModel.insert(items)
                        //Toast.makeText(activity, "Item added to cart successfully", Toast.LENGTH_LONG).show()
                    }

                    override fun onProductDetailsClicked(productId: String?) {
                        if (productId != null) {
                            val bundle = Bundle()
                            bundle.putString("SELECTED_PRODUCT_ID", productId)
                            /* findNavController().navigate(
                                 R.id.navigation_product_details, bundle
                             )*/

                            securedSharedPreferenceWrapper.putString(
                                "SELECTED_PRODUCT_ID",
                                productId
                            )
                            val intent =
                                Intent(requireContext(), ProducatDetailsActivity::class.java)
                            startActivity(intent)
                            //resultLauncher.launch(intent)
                            requireActivity().finish()

                            //startActivity(Intent(requireActivity(), ProducatDetailsActivity::class.java))
                            Log.e(TAG, "categoryItemOnClick: ${productId}")
                        }
                    }

                })

        binding.bestRecyclerView.adapter = subcatListAdapter

        //features list
        val ll2 = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.featuresRecyclerView.layoutManager = ll2
        featureListAdapter =
            FeaturesListAdapter(cartData, object : FeaturesListAdapter.MoreClickEventListener {
                override fun onAddToCartClicked(
                    languageList: SubCatProductsResponseModel?, cartQty: String?
                ) {
                    val gstAmountCal: String = (((languageList?.tax_percentage?.toDouble()
                        ?: 0.0) * (languageList?.offerPrice?.toDouble()
                        ?: 0.0)) / 100).roundToLong().toString()

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
                    if (cartQty == "0")
                        viewModel.deleteProduct(items.product_id)
                    else
                        viewModel.insert(items)
                    //Toast.makeText(activity, "Item added to cart successfully", Toast.LENGTH_LONG).show()
                }

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

                override fun onWishListClicked(
                    wishList: SubCatProductsResponseModel?
                ) {

                    wishList?.productsId?.let {
                        homeViewModel.callwishlistSaveListApi(
                            activity as Home_Activity,
                            securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, ""),
                            it
                        )
                    }
                }

            })
        binding.featuresRecyclerView.adapter = featureListAdapter

    }


    private fun addObservers() {

        homeViewModel.bannerModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                setBannersData(it.Response)
            }
        }

        homeViewModel.categoryModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                categoriesAdapter.addList(it.Response)
            } else {
                CommonMethods.hideProgress()
            }
        }
        homeViewModel.wishlistsaveModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                Toast.makeText(activity as Home_Activity, "WishList Item added", Toast.LENGTH_LONG)
                    .show()
            } else {
                CommonMethods.hideProgress()
            }
        }
        homeViewModel.subbestproductModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_subcatGOT", "data = ${it.Response}")
                featureListAdapter.addList(it.Response)
                subcatListAdapter.addList(it.Response)
            } else {
                CommonMethods.hideProgress()
            }
        }
    }

    //bannerd
    private fun setBannersData(bannerslist: List<BannersResponseModel>?) {

        if (bannerslist != null) {
            if (bannerslist.size > 0) {

//                latest
                val imageList = ArrayList<SlideModel>()
                for (element in bannerslist) {
                    imageList.add(SlideModel(ApiConstants.IMAGEBANNERURL + element.slider_image))
                    binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
                }

                binding.imageSlider.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                    }

                    fun doubleClick(position: Int) {
                    }
                })


                //old
                imageViewPagerAdapter =
                    ImageViewPagerAdapter(bannerslist as List<BannersResponseModel>, activity)
                binding.sliderRecyckerview.setAdapter(imageViewPagerAdapter)
                startAutoScroll()

            } else {
                binding.sliderRecyckerview.visibility = View.GONE
            }
        } else {
            binding.sliderRecyckerview.visibility = View.GONE
        }
    }

    private fun startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, 3000)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (isAutoScrolling) {
                if (positionss == imageViewPagerAdapter.itemCount - 1) {
                    positionss = 0
                } else {
                    positionss++
                }
                try {
                    binding.sliderRecyckerview.smoothScrollToPosition(positionss)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                handler.postDelayed(this, 3000)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as Home_Activity).setHeaderTitleVisible("Home")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 123) {
                // There are no request codes
            }
        }

}