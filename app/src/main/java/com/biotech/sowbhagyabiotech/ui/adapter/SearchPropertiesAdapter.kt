package com.biotech.sowbhagyabiotech.ui.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.SearchSubcategoryItemBinding
import com.biotech.sowbhagyabiotech.models.SubCatProductsResponseModel
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import java.util.Locale

class SearchPropertiesAdapter (var cartData:ArrayList<CartItems>?,
                               var categoryClickEventListener: SearchPropertiesAdapter.MoreClickEventListener?
): RecyclerView.Adapter<SearchPropertiesAdapter.ViewHolder>(),
    Filterable {
    var allsearchList: ArrayList<SubCatProductsResponseModel> = arrayListOf()
    private lateinit var searchListFiltered: List<SubCatProductsResponseModel>

    private var statuss: String = "Add"

    fun setData(allProperties: ArrayList<SubCatProductsResponseModel>) {
        this.allsearchList = allProperties
        notifyDataSetChanged()
    }

    fun setCartData(cartDatas: List<CartItems>) {
        cartData?.clear()
        cartData?.addAll(cartDatas)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchPropertiesAdapter.ViewHolder {
        val binding = SearchSubcategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchPropertiesAdapter.ViewHolder, position: Int) {
        holder.bindData(allsearchList[position])
    }

    override fun getItemCount(): Int = allsearchList.size

    inner class ViewHolder(val binding: SearchSubcategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: SubCatProductsResponseModel) {
            Glide.with(binding.ivProduct).load(item.productImage).placeholder(R.drawable.logo)
                .into(binding.ivProduct)
            binding.productName.text = item.productName
            binding.tvPrice.text = binding.tvPrice.context.getString(R.string.rs) +item.offerPrice
            //sale price
            binding.tvSaleprice.text = binding.tvSaleprice.context.getString(R.string.rs) +item.salesPrice
            binding.tvSaleprice.paintFlags = binding.tvSaleprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


            val cartQty = intArrayOf(binding.qty.text.toString().toInt())

            for (j in cartData!!.indices) {
                if (cartData!!.get(j).getProduct_id()
                        .toInt() == (allsearchList.get(position).productsId?.toInt())
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
                Log.e("item_cart_stock==>", "" + allsearchList.get(position).productsId)
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

            binding.root.setOnClickListener {
                categoryClickEventListener?.onProductDetailsClicked(item.productsId)
            }
            binding.addtocartBT.setOnClickListener {
                try {


                    val carstQty: String = binding.qty.text.toString()
                    statuss = "Update"

                    if (carstQty == "0") {
                        if(statuss == "Update"){

                            categoryClickEventListener?.onAddToCartClicked(
                                allsearchList.get(position),
                                carstQty
                            )
                            statuss = "Add"
                            binding.addtocartbtnLL.visibility = View.GONE
                        }else{

                        }
                        /*Toast.makeText(binding.root.context, "Select quantity..", Toast.LENGTH_LONG)
                            .show()*/
                    } else {
                        categoryClickEventListener?.onAddToCartClicked(
                            allsearchList.get(position),
                            carstQty
                        )
                        Log.e("data", "" + allsearchList.get(position))
                        Log.e("carstQty", carstQty)
                        if(statuss.trim() == "Add")
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
        fun onProductDetailsClicked(productId: String?)
        fun onAddToCartClicked(languageList: SubCatProductsResponseModel?, cartQty: String?)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()

            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {

                    searchListFiltered = allsearchList

                } else {
                    val filteredList: ArrayList<SubCatProductsResponseModel> =
                        ArrayList<SubCatProductsResponseModel>()


                    for (row: SubCatProductsResponseModel in allsearchList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.productName?.lowercase(Locale.getDefault())!!
                                .contains(charString.lowercase(Locale.getDefault())) || (row.productName!!.lowercase(
                                Locale.getDefault()
                            ).contains(charString.lowercase(Locale.getDefault())))
                        ) {

                            Log.e("filter name", row.productName!!)
                            filteredList.add(row)

                        }

                    }

                    searchListFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = searchListFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence, filterResults: FilterResults
            ) {

                if (filterResults.count == 0)
                else {
                    searchListFiltered = filterResults.values as List<SubCatProductsResponseModel>
                    notifyDataSetChanged()
                }


            }
        }
    }

}