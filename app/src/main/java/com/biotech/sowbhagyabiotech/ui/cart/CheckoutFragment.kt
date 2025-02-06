package com.biotech.sowbhagyabiotech.ui.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.CheckoutScreenBinding
import com.biotech.sowbhagyabiotech.roomdb.AppRoomDataBase
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.Cart_Adapter
import com.biotech.sowbhagyabiotech.ui.adapter.Checkout_Adapter
import com.biotech.sowbhagyabiotech.ui.home.HomeViewModel
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.Utilities
import java.util.Date
import java.util.Locale
import kotlin.math.roundToLong

class CheckoutFragment : Fragment(), Cart_Adapter.ProductItemClick {

    private var _binding: CheckoutScreenBinding? = null
    lateinit var sowbhgyaDB: AppRoomDataBase
    var viewModel: CartViewModel? = null
    var productIDSB: StringBuilder? = null
    var quantitySB: StringBuilder? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    var cartData: List<CartItems> = ArrayList<CartItems>()
    lateinit var productIDStr: String
    lateinit var productQtyStr: String
    lateinit var address: String
    lateinit var username: String
    lateinit var category_id: String
    lateinit var customerid: String
    var cartItemsList: List<CartItems> = ArrayList()
    lateinit var usersubcategory_id_: String
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = CheckoutScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")
        category_id = securedSharedPreferenceWrapper.getString("categoryid", "")!!
        customerid = securedSharedPreferenceWrapper.getString("userid", "").toString()
        address = securedSharedPreferenceWrapper.getString("address", "").toString()
        username = securedSharedPreferenceWrapper.getString("username", "").toString()
        binding.currentdateTx.text = "${
            java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                Date(System.currentTimeMillis())
            )
        }"

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val appRoomDataBase: AppRoomDataBase by lazy {
            AppRoomDataBase.getInstance(requireActivity())
        }
        appRoomDataBase.cropTableDao()

        viewModel = CartViewModel(activity as Activity)
        viewModel!!.cartData()

        val cartVM = CartViewModel(activity as Activity, false)
        cartVM.cartData()
        cartVM.getCartItems.observe(requireActivity(), { cartItems -> cartData = cartItems })


        try {

            val id = securedSharedPreferenceWrapper.getInt("id", 0).toString()
            val fullnames = securedSharedPreferenceWrapper.getString("fullname", "").toString()
            val mnum = securedSharedPreferenceWrapper.getString("mnum", "").toString()
            val hn = securedSharedPreferenceWrapper.getString("hn", "").toString()
            val lmark = securedSharedPreferenceWrapper.getString("lmark", "").toString()
            val loc = securedSharedPreferenceWrapper.getString("loc", "").toString()
            val cit = securedSharedPreferenceWrapper.getString("cit", "").toString()
            val stat = securedSharedPreferenceWrapper.getString("stat", "").toString()
            val pin = securedSharedPreferenceWrapper.getString("pin", "").toString()


            if (hn.isEmpty()) {
                binding.adressdata.text = "Choose Address"
                binding.changeAddress.visibility = View.GONE
            } else {
                binding.changeAddress.visibility = View.VISIBLE
                binding.adressdata.text =
                    ("Name: "+fullnames + ",\n" + "Mobile No: "+mnum + ",\n" + "Address: "+hn + "," + lmark + "," + loc + "," + cit + "," + stat + "," + pin + ".")
            }

            binding.paynow.setOnClickListener {
                if (hn.isEmpty()) {
                    Toast.makeText(requireActivity(), "Please select Address", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    homeViewModel.callPlaceOrderApi(
                        requireActivity(),
                        securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, ""),
                        productIDStr,
                        productQtyStr,
                        id
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {


            viewModel!!.getCartItems?.observe(requireActivity()) { cartItems ->
                cartItemsList = cartItems
                if (cartItems.size > 0) {
                    try {
                        binding.totalprice.text = "" + cartItems.size.toString() + " Items"
                        getTotalPrice(cartItemsList)

                        val adapter = Checkout_Adapter(
                            activity as Activity, cartItems as ArrayList<CartItems>, cartData
                        )
                        binding.checkoutRecycler.adapter = adapter

                    } catch (e: java.lang.NullPointerException) {
                        e.printStackTrace()
                    }
                } else {

                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        addObservers()

        binding.adressdata.setOnClickListener {
            homeViewModel.callgetAddressListApi( requireActivity(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, ""))
        }

        binding.changeAddress.setOnClickListener {
            homeViewModel.callgetAddressListApi( requireActivity(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, ""))
        }

        return root
    }

    private fun addObservers() {

        homeViewModel.addressModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()

                val intent = Intent(activity, Payment_activity::class.java)
                intent.putExtra("address",  binding.adressdata.text)
                intent.putExtra("Gst",  binding.totalGST.text)
                intent.putExtra("ProductsPrice",  binding.totalpricee.text)
                intent.putExtra("TotalPrice",  binding.totalprice.text)
                startActivity(intent)

                val viewModel = CartViewModel(activity as Activity)
                viewModel.delete(cartItemsList[0], true, true)

                /*    securedSharedPreferenceWrapper.putString(
                        "Orderid", it.order_id.toString()
                    )
                    securedSharedPreferenceWrapper.putString(
                        "orderamount", response.body()?.response?.amount.toString()
                    )*/

                Toast.makeText(
                    context, "Order Placed Sucessfully", Toast.LENGTH_LONG
                ).show()
            } else {
                CommonMethods.hideProgress()
            }
        }
        homeViewModel.getaddressModelResponse.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                if (it.Response!!.isEmpty()) {

                    val navController = Navigation.findNavController(
                        context as Activity, R.id.nav_host_fragment_activity_home
                    )
                    navController.navigate(R.id.navigation_getadresslist)

                } else {

                    val navController = Navigation.findNavController(
                        context as Activity, R.id.nav_host_fragment_activity_home
                    )
                    navController.navigate(R.id.navigation_getadresslist)

                }

            } else {
                CommonMethods.hideProgress()

            }
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
            binding.totalGST.text = "\u20b9 $gst_total"
            var total_amount_display = sum + gst_total
            binding.totalpricee.text = "\u20b9 $sum"
            binding.totalprice.text = "\u20b9 $total_amount_display"

        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


    override fun onProductItemClick(itemsData: CartItems?) {
//        ProductinDetails_Fragment().getData(itemsData?.product_id.toString(), true)!!
//
//        val navController =
//            Navigation.findNavController(
//                context as Activity,
//                R.id.nav_host_fragment_content_home_screen
//            )
//        navController.navigate(R.id.nav_product_details)
//
//        val editor = sharedPreferences.edit()
//        editor.putString("subcatid", itemsData?.product_id)
//        editor.clear()
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
        val viewModel = CartViewModel(activity)
        viewModel.insert(items)
       // Toast.makeText(activity, "Item added to cart successfully", Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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