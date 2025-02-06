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
import com.biotech.sowbhagyabiotech.databinding.SubcategoryItemBinding
import com.biotech.sowbhagyabiotech.models.SubCatProductsResponseModel
import com.biotech.sowbhagyabiotech.roomdb.CartItems


class SubCatTabListAdapter(
    var cartData: List<CartItems>?,
    var categoryClickEventListener: MoreClickEventListener?

) : RecyclerView.Adapter<SubCatTabListAdapter.ViewHolder>(

) {
    private var values: ArrayList<SubCatProductsResponseModel> = ArrayList()

    private var statuss: String = "Add"

    fun addList(news: ArrayList<SubCatProductsResponseModel>) {
        values = news
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = SubcategoryItemBinding.inflate(
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

    inner class ViewHolder(private val binding: SubcategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: SubCatProductsResponseModel) {

            Glide.with(binding.ivProduct.context).load(item.productImage)
                .into(binding.ivProduct)

            binding.productName.text = item.productName
            binding.tvPrice.text = binding.tvPrice.context.getString(R.string.rs) +  item.offerPrice
            //sale price
            binding.tvSaleprice.text = binding.tvSaleprice.context.getString(R.string.rs) +item.salesPrice
            binding.tvSaleprice.paintFlags = binding.tvSaleprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


            binding.root.setOnClickListener {
                categoryClickEventListener?.onProductDetailsClicked(item.productsId)
            }
            val cartQty = intArrayOf(binding.qty.text.toString().toInt())
            for (j in cartData!!.indices) {
                if (cartData!!.get(j).getProduct_id()
                        .toInt() == (values.get(position).productsId?.toInt())
                ) {
                    binding.qty.text = "" + cartData!![j].getCartQty()

                    statuss = "Update"
                    Log.e("addtocart qty", "" + cartData?.get(j)?.getCartQty())
                    //binding.addBtn.visibility=View.GONE
                    //binding.addBtn.text = "Update"
                    binding.addtocartbtnLL.visibility=View.VISIBLE
                } else {
                    statuss = "Add"
                    //  binding.addBtn.visibility=View.VISIBLE
                }
            }

            if(binding.qty.text.toString().trim().toInt() > 0){
                statuss = "Update"
            }else{
                statuss = "Add"
            }

            binding.plus.setOnClickListener {
                statuss = "Update"
                val carstQty1: String = binding.qty.text.toString()
                Log.e("item_cart_stock==>", "" + values.get(position).productsId)
                Log.e("item_cart_cart==>", "" + carstQty1)
                if(binding.qty.text.toString().toInt() > 0){
                    binding.qty.text = (binding.qty.text.toString().toInt() +1).toString()
                }

                /* cartQty[0]++
                 binding.qty.text = "" + cartQty.get(0)*/
                //binding.addtocartBT.performClick()

            }
            binding.minusTxt.setOnClickListener { v ->
                statuss = "Update"
                /* if (cartQty[0] > 0) {
                     cartQty[0]--
                 }*/
                val carstQty: String = binding.qty.text.toString()
                //binding.qty.text = "" + cartQty[0]

                if(binding.qty.text.toString().toInt() > 1){
                    binding.qty.text = (binding.qty.text.toString().toInt() -1).toString()
                }
                /*categoryClickEventListener?.onAddToCartClicked(
                    values.get(position),
                    carstQty
                )*/
            }
            binding.addBtn.setOnClickListener {
                binding.addtocartbtnLL.visibility = View.VISIBLE
                // binding.addBtn.visibility = View.GONE

                if(statuss.trim() == "Add"){
                    cartQty[0]++
                    binding.qty.text = "" + cartQty.get(0)
                    statuss = "Update"
                }else{

                }
                binding.addtocartBT.performClick()
                //Toast.makeText(binding.addBtn.context, "Successfully added into card", Toast.LENGTH_SHORT).show()

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
                        Toast.makeText(binding.addtocartBT.context, "Successfully added into Cart", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }*/


                try {


                    val carstQty: String = binding.qty.text.toString()
                    statuss = "Update"

                    if (carstQty == "0") {
                        if(statuss == "Update"){

                            categoryClickEventListener?.onAddToCartClicked(
                                values.get(position),
                                carstQty
                            )
                            statuss = "Add"
                            binding.addtocartbtnLL.visibility = View.INVISIBLE
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
                        if(statuss.trim() == "Add")
                            Toast.makeText(binding.addtocartBT.context, "Successfully added into Cart", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(binding.addtocartBT.context, "Successfully Updated Cart", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            }

        }
    }


    interface MoreClickEventListener {
        fun onProductDetailsClicked(productId: String?)
        fun onAddToCartClicked(languageList: SubCatProductsResponseModel?, cartQty: String?)
    }
}
