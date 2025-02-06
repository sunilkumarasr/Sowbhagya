package com.biotech.sowbhagyabiotech.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.CartListItemBinding
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel

class Cart_Adapter(
    val context: Context,
    var itemsData: ArrayList<CartItems>,
    var cartData: List<CartItems>?,
    var click: ProductItemClick?
) : RecyclerView.Adapter<Cart_Adapter.ViewHolder>() {
    var serviceidlist: MutableList<String> = ArrayList()
    lateinit var usersubcategory_id_: String

    // create an inner class with name ViewHolder
    //It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: CartListItemBinding) : RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    companion object;

    // bind the items with each item of the list languageList which than will be
    // shown in recycler view
    // to keep it simple we are not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            with(itemsData[position]) {
                // set name of the language from the list
//                binding.brndsTitleText.text = languageList.get(position).prodcut_name
//                binding.brandNameText.text = languageList.get(position).prodcut_desc

                val data: CartItems = itemsData.get(position)

                holder.binding.cartQty.text = "" + data.cartQty

                val cartQty = intArrayOf(holder.binding.cartQty.text.toString().toInt())
                Log.e("item_cart_qty==>", "" + cartQty[0])
                holder.binding.root.setOnClickListener { v ->
                    click!!.onProductItemClick(
                        itemsData.get(
                            position
                        )
                    )
                }
                holder.binding.nameTVID.text = data.getItemName()
                holder.binding.qtyTVID.text = data.cartQty.toString() + ""

//        holder.binding.priceTVID.setText("Total : \u20b9 " + data.getFinal_price());

                //        holder.binding.priceTVID.setText("Total : \u20b9 " + data.getFinal_price());
                try {
                    val final_amt: Int = data.getPrice().toInt() * data.cartQty.toInt()
                    val gstEach: Int = data.getGstAmount().toInt() * data.cartQty.toInt()
                    holder.binding.priceTVID.text = "Total : \u20b9 $final_amt"
                   // val  gstAmoutData = gstEach * data.cartQty.toInt()
                    holder.binding.gstTVID.text = "GST : \u20b9 $gstEach"
                    Log.e("item_price", "" + final_amt)
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
                holder.binding.weightVID.text = "" + data.weight


                holder.binding.removeTVID.setOnClickListener { v ->
                    val builder1 = AlertDialog.Builder(context)
                    builder1.setCancelable(false)
                    builder1.setTitle(null)
                    builder1.setMessage("Are you sure \nDo you want to delete this item from cart")
                    builder1.setPositiveButton(
                        "Yes"
                    ) { dialogInterface: DialogInterface?, i1: Int ->
                        val viewModel = CartViewModel(context as Activity?)
                        viewModel.delete(data, false, false)
                    }
                    builder1.setNegativeButton("No", null)
                    builder1.create().show()
                }

                Glide.with(holder.binding.imageID.context).load(data.getItemImage())
                    .placeholder(R.drawable.logo).into(holder.binding.imageID)

                holder.binding.plus.setOnClickListener { v ->


                    val carstQty = binding.cartQty.text.toString()
                    if (data.stock == carstQty) {

                        Toast.makeText(
                            context, "Stock Limit only " + data.stock, Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.e("item_cart_==>", "" + carstQty[0])

                        cartQty[0]++
                        holder.binding.cartQty.text = "" + cartQty.get(0)
                        holder.binding.addToCartBtn.performClick()
                    }


                }

                holder.binding.addToCartBtn.text = "Update"

                holder.binding.minusTxt.setOnClickListener { v ->
                    if (cartQty[0] > 1) {
                        cartQty[0]--

                        holder.binding.cartQty.text = "" + cartQty[0]
                        holder.binding.addToCartBtn.performClick()
                    }
                }

                holder.binding.addToCartBtn.setOnClickListener { v ->
                    val carstQty = holder.binding.cartQty.text.toString()
                    if (carstQty == "0") {

                        Toast.makeText(context, "Select quantity..", Toast.LENGTH_LONG).show()

                    } else {
                        click!!.onAddToCartClicked(data, carstQty)
                    }
                }


            }
        }
    }

    interface ProductItemClick {
        fun onProductItemClick(itemsData: CartItems?)
        fun onAddToCartClicked(itemsData: CartItems?, cartQty: String?)
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return itemsData.size
    }
}