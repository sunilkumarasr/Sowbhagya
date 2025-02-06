package com.biotech.sowbhagyabiotech.ui.product

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.base.BaseActivity
import com.biotech.sowbhagyabiotech.databinding.ActivityProducatDetailsBinding
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.ConstantUtils
import com.biotech.sowbhagyabiotech.utils.Utilities
import kotlin.math.roundToLong



class ProducatDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityProducatDetailsBinding? = null
    lateinit var baseActivity: BaseActivity

    private lateinit var sharedPreferences: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var producatDetailsViewModel: ProductDetailsViewModel
    private var cartQty: Int = 1
    private var productId: String? = null
    private var productName: String? = null
    private var productImage: String? = null
    private var offerPrice: String? = null
    private var  productNum: String? = null
    private var  tax_percentage: String? = null
    private val binding get() = _binding!!
    private lateinit var productDetailsModel: ProductDetailsViewModel
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    var cartData = ArrayList<CartItems>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProducatDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ConstantUtils.changeNotificationBarColor(
            this,
            ContextCompat.getColor(this, R.color.tab_hilet_color),
            false
        )
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)


        val cartVM = CartViewModel(this, false)
        cartVM.cartData()
        cartVM.getCartItems.observe(this) {
                cartItems -> cartData.addAll(cartItems)
            updateCartValues(cartData)

            binding.cartCount.setText(cartItems.size.toString())
        }

        binding.ivWhatsApp.setOnClickListener { val uri = Uri.parse(applicationContext.getString(R.string.whatasapp))
            val i = Intent(Intent.ACTION_SENDTO, uri)
            i.setPackage("com.whatsapp")
            startActivity(Intent.createChooser(i, "")) }

        binding.frameLayoutCart.setOnClickListener {
            val intent = Intent(this, Home_Activity::class.java)
            startActivity(intent)
            finish()
        }

        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this, "sowbhagya")

        this.productDetailsModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]

        productId = securedSharedPreferenceWrapper.getString("SELECTED_PRODUCT_ID","")
        addObservers()

        productDetailsModel.callProductDetailsApi(this, productId = productId!!)

        binding.plus.setOnClickListener {

            cartQty++
            binding.qty.text = "" + cartQty
            //binding.addtocartBT.performClick()

        }

        binding.minusTxt.setOnClickListener { v ->

            if (cartQty > 1) {
                cartQty--
            }

//            if (cartQty==0){
//                binding.addtocartbtnLL.visibility= View.GONE
//                binding.addBtn.visibility=View.VISIBLE
//            }

            binding.qty.text = "" + cartQty
        }
        binding.addBtn.setOnClickListener {
//            binding.addtocartbtnLL.visibility= View.VISIBLE
//            binding.addBtn.visibility= View.GONE

            val snackbar = Snackbar.make(
                view,
                "Successfully added into Cart",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
            snackbar.setBackgroundTint(resources.getColor(R.color.green))

            binding.qty.text = "" + cartQty
            callAPICartUpdate()
        }
        binding.ivClose.setOnClickListener {
            /*val intent = Intent()
            setResult(123, intent)
            finish()*/
            val intent = Intent(applicationContext, Home_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun callAPICartUpdate() {
        val gstAmountCal: String = (((tax_percentage?.toDouble() ?: 0.0) * (offerPrice?.toDouble()?:0.0))/100).roundToLong().toString()
        val items = CartItems(
            0,
            "0",
            Utilities.getDeviceID(this),
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

        val viewModel = CartViewModel(this)
        if(cartQty==0)
            viewModel.deleteProduct(items.product_id)
        else
            viewModel.insert(items)
    }

    private fun updateCartValues(cartData: ArrayList<CartItems>) {
        for (j in cartData!!.indices) {
            if (cartData!!.get(j).getProduct_id()
                    .toInt() == productId!!.toInt()) {


                this.runOnUiThread(
                    object : Runnable {
                        override fun run() {
                            binding.qty.text = "" + cartData[j].getCartQty()
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

        productDetailsModel.productDetailsResponseModel.observe(this) {
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
                    binding.tvOfferPrice.text = binding.tvOfferPrice.context.getString(R.string.rs) + it.Response[0].offer_price
                    binding.tvSalesprice.text = binding.tvSalesprice.context.getString(R.string.rs) + it.Response[0].sales_price
                    binding.tvSalesprice.paintFlags = binding.tvSalesprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


                    if (it.Response[0].stock.equals("In stock")){
                        binding.tvStock.text = it.Response[0].stock
                    }else{
                        binding.tvStock.text = "out of stock"
                        binding.tvStock.setTextColor(Color.parseColor("#FFCC0000"));
                        binding.addBtn.setVisibility(View.GONE);
                        binding.addtocartbtnLL.setVisibility(View.GONE);
                    }


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


    override fun onResume() {
        super.onResume()
        //(activity as Home_Activity).setHeaderTitleVisible("Notifications")
    }


    override fun onPause() {
        super.onPause()
        // (activity as Home_Activity).setHeaderTitleGone()

    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, Home_Activity::class.java)
        startActivity(intent)
        finish()
    }


}