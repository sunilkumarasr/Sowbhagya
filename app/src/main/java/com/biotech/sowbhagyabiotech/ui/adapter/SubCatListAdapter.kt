package com.biotech.sowbhagyabiotech.ui.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.ProductItemBinding
import com.biotech.sowbhagyabiotech.models.SubCatProductsResponseModel
import com.biotech.sowbhagyabiotech.roomdb.CartItems


class SubCatListAdapter(
    val cartData: ArrayList<CartItems>?,
    var categoryClickEventListener: MoreClickEventListener?

) : RecyclerView.Adapter<SubCatListAdapter.ViewHolder>(

) {
    private var values: ArrayList<SubCatProductsResponseModel> = ArrayList()

    fun addList(news: ArrayList<SubCatProductsResponseModel>) {
        values = news
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(
            binding
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(values[position])
    }

    override fun getItemCount(): Int = values.size
    fun setCartData(cartDatas: List<CartItems>) {
        cartData?.clear()
        cartData?.addAll(cartDatas)
        notifyDataSetChanged()

    }

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: SubCatProductsResponseModel) {

            Glide.with(binding.ivProduct.context).load(item.productImage)
                .into(binding.ivProduct)

            binding.tvProduct.text = item.productName
            binding.offerTxt.text = binding.offerTxt.context.getString(R.string.rs) + item.offerPrice
            //sale price
            binding.priceTxt.text = binding.priceTxt.context.getString(R.string.rs) + item.salesPrice
            binding.priceTxt.paintFlags = binding.priceTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


            for (j in cartData!!.indices) {
                if (cartData!!.get(j).getProduct_id()
                        .toInt() == (values.get(position).productsId?.toInt())
                ) {
                    binding.qty.text = "" + cartData!![j].getCartQty()

                    binding.addBtn.text = "Update"
                    Log.e("addtocart qty", "" + cartData?.get(j)?.getCartQty())
                    //binding.addBtn.visibility=View.GONE
                    //binding.addBtn.text = "Update"

                    //
//                    binding.addtocartbtnLL.visibility=View.VISIBLE


                } else {
                    binding.addtocartBT.text = "Add"
                }
            }
            val cartQty = intArrayOf(binding.qty.text.toString().toInt())
            if(binding.qty.text.toString().trim().toInt() > 0){
                binding.addBtn.text = "Update"
            }else{
                binding.addBtn.text = "Add"
            }
            binding.plus.setOnClickListener {

                binding.addBtn.text ="Update"

                val carstQty1: String = binding.qty.text.toString()
                Log.e("item_cart_stock==>", "" + values.get(position).productsId)
                Log.e("item_cart_cart==>", "" + carstQty1)
                if(binding.qty.text.toString().toInt() > 0){
                    binding.qty.text = (binding.qty.text.toString().toInt() +1).toString()
                }

            }
            binding.wishListIV.visibility=View.GONE

            binding.minusTxt.setOnClickListener { v ->
                binding.addBtn.text ="Update"
                /* if (cartQty[0] > 0) {
                     cartQty[0]--
                 }*/
                val carstQty: String = binding.qty.text.toString()
                //binding.qty.text = "" + cartQty[0]

                if(binding.qty.text.toString().toInt() > 0){
                    binding.qty.text = (binding.qty.text.toString().toInt() -1).toString()
                }
            }
            binding.root.setOnClickListener {
                categoryClickEventListener?.onProductDetailsClicked(item.productsId)
            }

            binding.addBtn.setOnClickListener {
                binding.addtocartbtnLL.visibility = View.VISIBLE
                // binding.addBtn.visibility = View.GONE

                if(binding.addBtn.text.toString().trim() == "Add"){
                    cartQty[0]++
                    binding.qty.text = "" + cartQty.get(0)
                    binding.addBtn.text.toString().trim() == "Update"
                }else{

                }
                binding.addtocartBT.performClick()
            }
            binding.addtocartBT.setOnClickListener {
               /* try {


                    val carstQty: String = binding.qty.text.toString()

                    if (carstQty == "0") {
                        Toast.makeText(binding.root.context, "Select quantity..", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        categoryClickEventListener?.onAddToCartClicked(
                            values.get(position),
                            carstQty
                        )
                        Log.e("data", "" + values.get(position))
                        Log.e("carstQty", carstQty)
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }*/

                try {


                    val carstQty: String = binding.qty.text.toString()
                    binding.addBtn.text = "Update"

                    if (carstQty == "0") {
                        if(binding.addBtn.text.toString() == "Update"){

                            categoryClickEventListener?.onAddToCartClicked(
                                values.get(position),
                                carstQty
                            )
                            binding.addBtn.text = "Add"
                            binding.addtocartbtnLL.visibility = View.GONE
                        }else{

                        }
                        /*Toast.makeText(binding.root.context, "Select quantity..", Toast.LENGTH_LONG)
                            .show()*/
                    } else {
                        categoryClickEventListener?.onAddToCartClicked(
                            values.get(position),
                            carstQty
                        )
                        Log.e("data", "" + values.get(position))
                        Log.e("carstQty", carstQty)
                        if(binding.addBtn.text.toString().trim() == "Add")
                            Toast.makeText(binding.addBtn.context, "Successfully added into Cart", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(binding.addBtn.context, "Successfully Updated Cart", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            }

        }
    }


    interface MoreClickEventListener {
        fun onAddToCartClicked(languageList: SubCatProductsResponseModel?, cartQty: String?)
        fun onProductDetailsClicked(productId: String?)

    }
    interface ProductDetailsEventListener{
        fun onProductDetailsClicked(productId: String?)
    }
}
