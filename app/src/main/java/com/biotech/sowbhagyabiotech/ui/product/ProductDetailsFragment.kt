package com.biotech.sowbhagyabiotech.ui.product

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.FragmentProductDetailsBinding
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.Utilities
import kotlin.math.roundToLong

class ProductDetailsFragment : Fragment() {


    private var cartQty: Int = 0
    private var productId: String? = null
    private var productName: String? = null
    private var productImage: String? = null
    private var offerPrice: String? = null
    private var  productNum: String? = null
    private var  tax_percentage: String? = null
   private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productDetailsModel: ProductDetailsViewModel
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    var cartData = ArrayList<CartItems>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductDetailsBinding.inflate(inflater, container,false)
        val root: View = binding.root


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productDetailsModel = ViewModelProvider(this).get(ProductDetailsViewModel::class.java)

        val cartVM = CartViewModel(activity, false)
        cartVM.cartData()
        cartVM.getCartItems.observe(requireActivity()) {
                cartItems -> cartData.addAll(cartItems)
            updateCartValues(cartData)

        }
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        this.productDetailsModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]

        productId = securedSharedPreferenceWrapper.getString("SELECTED_PRODUCT_ID","")
        addObservers()

        productDetailsModel.callProductDetailsApi(
            requireContext(), productId = productId!!
        )



        binding.plus.setOnClickListener {

            val carstQty1: String = binding.qty.text.toString()


            cartQty++
            binding.qty.text = "" + cartQty
            callAPICartUpdate()
            //binding.addtocartBT.performClick()

        }


        binding.minusTxt.setOnClickListener { v ->
            if (cartQty > 0) {
                cartQty--
            }
            binding.qty.text = "" + cartQty
            val carstQty: String = binding.qty.text.toString()
            callAPICartUpdate()

        }
        binding.addBtn.setOnClickListener {
            binding.addtocartbtnLL.visibility=View.VISIBLE
            binding.addBtn.visibility=View.GONE
            val carstQty1: String = binding.qty.text.toString()
            Toast.makeText(binding.addBtn.context, "Successfully added into Cart", Toast.LENGTH_SHORT).show()
            cartQty++
            binding.qty.text = "" + cartQty
            callAPICartUpdate()
        }
    }

    private fun callAPICartUpdate() {
        val gstAmountCal: String = (((tax_percentage?.toDouble() ?: 0.0) * (offerPrice?.toDouble()?:0.0))/100).roundToLong().toString()
        val items = CartItems(
            0,
            "0",
            Utilities.getDeviceID(activity),
            ApiConstants.PRODUCTS_CART,
            productName,
            productImage,
            cartQty.toString(),
            offerPrice,
            offerPrice,
            productId,
            productNum,
            tax_percentage,
            gstAmountCal

        )

        val viewModel = CartViewModel(activity)
        if(cartQty==0)
            viewModel.deleteProduct(items.product_id)
        else
            viewModel.insert(items)
    }

    private fun updateCartValues(cartData: ArrayList<CartItems>) {
        for (j in cartData!!.indices) {
            if (cartData!!.get(j).getProduct_id()
                    .toInt() == productId!!.toInt()) {


                requireActivity().runOnUiThread(
                    object : Runnable {
                        override fun run() {
                            binding.qty.text = "" + cartData[j].getCartQty()
                            binding.addtocartbtnLL.visibility=View.VISIBLE
                            binding.addBtn.visibility=View.GONE
                        }
                    }
                )

            } else {
               // binding.addBtn.text = "Add"
            }
        }
        cartQty = binding.qty.text.toString().toInt()
    }

    private fun addObservers() {

        productDetailsModel.productDetailsResponseModel.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                if (it.Status!!){
                    Glide.with(binding.ivProductImage.context).load(it.Response[0].product_image_full_path)
                        .into(binding.ivProductImage)
                   productName = it.Response[0].product_name
                    productImage= it.Response[0].product_dimage
                    offerPrice = it.Response[0].offer_price
                   productNum  = it.Response[0].product_num
                    tax_percentage  = it.Response[0].tax_percentage

                    binding.tvProductName.text = it.Response[0].product_name
                    binding.tvRating.text = it.Response[0].rating
                    binding.tvPrice.text = "Price : "+binding.tvPrice.context.getString(R.string.rs) + it.Response[0].offer_price

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        binding.tvDescriptiion.text = Html.fromHtml(
                            it.Response[0].product_description,
                            Html.FROM_HTML_MODE_COMPACT
                        )
                        binding.tvInformation.text = Html.fromHtml(
                            it.Response[0].product_information,
                            Html.FROM_HTML_MODE_COMPACT
                        )
                    } else {
                        binding.tvDescriptiion.text =
                            Html.fromHtml(it.Response[0].product_description)
                        binding.tvInformation.text =
                            Html.fromHtml(it.Response[0].product_information)
                    }

                }else{

                }

            } else {
                CommonMethods.hideProgress()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    override fun onResume() {
        super.onResume()
        //(activity as Home_Activity).setHeaderTitleVisible("Notifications")
    }

    override fun onPause() {
        super.onPause()
       // (activity as Home_Activity).setHeaderTitleGone()

    }




}