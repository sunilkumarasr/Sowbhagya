package com.biotech.sowbhagyabiotech.ui.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.FragmentMycartBinding
import com.biotech.sowbhagyabiotech.roomdb.AppRoomDataBase
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.Cart_Adapter
import com.biotech.sowbhagyabiotech.utils.Utilities
import kotlin.math.roundToLong

class CartFragment : Fragment(), Cart_Adapter.ProductItemClick {

    private lateinit var binding: FragmentMycartBinding
    var cartData: List<CartItems> = ArrayList<CartItems>()
    lateinit var sowbhgyaDB: AppRoomDataBase
    var viewModel: CartViewModel? = null
    var cartItemsList: List<CartItems> = ArrayList()

    // This property is only valid between onCreateView and
    // onDestroyView.

    lateinit var productIDStr: String
    lateinit var productQtyStr: String
    lateinit var address: String
    lateinit var username: String
    lateinit var category_id: String
    lateinit var customerid: String
    var productIDSB: StringBuilder? = null
    var quantitySB: StringBuilder? = null
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    lateinit var usersubcategory_id_: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentMycartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(activity as Home_Activity, "sowbhagya")


        category_id = securedSharedPreferenceWrapper.getString("categoryid", "")!!
        customerid = securedSharedPreferenceWrapper.getString("userid", "").toString()
        address = securedSharedPreferenceWrapper.getString("address", "").toString()
        username = securedSharedPreferenceWrapper.getString("username", "").toString()

        Log.e("cust", customerid)
        Log.e("address", address)
        val appRoomDataBase: AppRoomDataBase by lazy {
            AppRoomDataBase.getInstance(activity as Home_Activity)
        }
        appRoomDataBase.cropTableDao()

        viewModel = CartViewModel(activity as Home_Activity, false)
        viewModel?.cartData()

        viewModel?.getCartItems?.observe(requireActivity(), { cartItems -> cartData = cartItems })
        addObservers()
        binding.checkout.setOnClickListener {

            val navController = Navigation.findNavController(
                context as Activity, R.id.nav_host_fragment_activity_home
            )
            navController.navigate(R.id.navigation_checkout)
        }

        return root
    }

    private fun addObservers() {

        try {
            viewModel!!.getCartItems?.observe(activity as Home_Activity) { cartItems ->
                cartItemsList = cartItems
                if (cartItems.size > 0) {

                    binding.itemCountTVID.text = "" + cartItems.size.toString() + " Items"
                    getTotalPrice(cartItemsList)


                    try {

                        val adapter = Cart_Adapter(
                            activity as Activity, cartItems as ArrayList<CartItems>, cartData, this
                        )

                        binding.cartRCID.adapter = adapter

                    } catch (e: java.lang.NullPointerException) {
                        e.printStackTrace()
                    }
                } else {
                    binding.pricelay.visibility = View.GONE
                    binding.checkout.visibility = View.GONE
                    binding.nodata.visibility = View.VISIBLE

                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }


    @SuppressLint("SetTextI18n")
    private fun getTotalPrice(cartItemsList: List<CartItems>) {
        try {
            var sum = 0.0
            var gst_total = 0.0
            for (i in cartItemsList.indices) {
                sum += cartItemsList[i].getPrice().toDouble() * cartItemsList[i].getCartQty()
                    .toDouble()
                gst_total += cartItemsList[i].gstAmount.toDouble()* cartItemsList[i].getCartQty()
                    .toDouble()

                val productsids = cartItemsList[i].product_id
                var usercategoryselectedList_id = ArrayList<String>()

                quantitySB = StringBuilder()
                productIDSB = StringBuilder()

                for (eachstring in cartItemsList) {
                    productIDSB?.append(eachstring.getProduct_id())?.append("##")
                    quantitySB?.append(eachstring.getCartQty())?.append("##")
                }

                productIDStr = productIDSB.toString()
                productQtyStr = quantitySB.toString()


                if (productIDStr.length > 0) productIDStr =
                    productIDStr.substring(0, productIDStr.length - 2)


                if (productQtyStr.length > 0) productQtyStr =
                    productQtyStr.substring(0, productQtyStr.length - 2)


                Log.e("quantity==>", "" + productIDStr)
                Log.e("ids==>", "" + productQtyStr)
                usercategoryselectedList_id.add(
                    cartItemsList[i].product_id
                )


                usersubcategory_id_ = usercategoryselectedList_id.toString().trim()

                    .replace("[", "")  //remove the right bracket
                    .replace(" ", "").trim()  //remove the commas
                    .replace(",", "#").trim()  //remove the commas
                    .replace("]", "")  //remove the left bracket
                    .trim()
                Log.e("prduct id", "" + usercategoryselectedList_id)
                Log.e("usersubcategory_id_", "" + usersubcategory_id_)



                Log.e("product-id", "" + productsids)
            }


            binding.productsAmount.text = "Products Amount: \u20b9 $sum"
            binding.totalGst.text = "GST : \u20b9 $gst_total"
            var total_amount_display = sum + gst_total
            binding.totalPriceTVID.text = "Total Amount: \u20b9 $total_amount_display"
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun onProductItemClick(itemsData: CartItems?) {

    }

    override fun onAddToCartClicked(itemsData: CartItems?, cartQty: String?) {
        val gstAmountCal: String = (((itemsData?.gst?.toDouble() ?: 0.0) * (itemsData?.offer_price?.toDouble()?:0.0))/100).roundToLong().toString()
        val items = CartItems(
            0,
            "0",
            Utilities.getDeviceID(activity),
            ApiConstants.PRODUCTS_CART,
            itemsData!!.itemName,
            itemsData.itemImage,
            cartQty,
            java.lang.String.valueOf(itemsData.offer_price),
            java.lang.String.valueOf(itemsData.offer_price),
            itemsData.product_id.toString(),
            itemsData.stock,
            itemsData.gst,
            gstAmountCal
        )
        val viewModel = CartViewModel(activity as Home_Activity)
        viewModel.insert(items)

        Toast.makeText(activity as Home_Activity, "Item added to cart successfully", Toast.LENGTH_SHORT).show()
    }


    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("My Cart")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()
    }
}