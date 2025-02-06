package com.biotech.sowbhagyabiotech.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.CheckoutadapterBinding
import com.biotech.sowbhagyabiotech.roomdb.CartItems

class Checkout_Adapter(
    val context: Context,
    var itemsData: ArrayList<CartItems>,
    var cartData: List<CartItems>?
) : RecyclerView.Adapter<Checkout_Adapter.ViewHolder>() {
    var serviceidlist: MutableList<String> = ArrayList()
    lateinit var usersubcategory_id_: String

    // create an inner class with name ViewHolder
    //It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: CheckoutadapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CheckoutadapterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    companion object;

    // bind the items with each item of the list languageList which than will be
    // shown in recycler view
    // to keep it simple we are not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(itemsData[position]) {
            // set name of the language from the list
//                binding.brndsTitleText.text = languageList.get(position).prodcut_name
//                binding.brandNameText.text = languageList.get(position).prodcut_desc

            val data: CartItems = itemsData.get(position)




            holder.binding.nameChekoutTV.text = data.getItemName()
//        holder.binding.priceTVID.setText("Total : \u20b9 " + data.getFinal_price());

            //        holder.binding.priceTVID.setText("Total : \u20b9 " + data.getFinal_price());
            try {
                val final_amt: Int = data.getPrice().toInt() * data.cartQty.toInt()
                holder.binding.qtyTV.text = "Quantity ${data.cartQty} x ₹ ${data.getPrice()}"
                holder.binding.priceTv.text = "\u20b9 $final_amt"
                val gstEach: Int = data.getGstAmount().toInt() * data.cartQty.toInt()
                holder.binding.tvGst.text = "GST : \u20b9 $gstEach"
                Log.e("item_price", "" + final_amt)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

            Glide.with(holder.binding.image.context)
                .load(data.getItemImage())
                .placeholder(R.drawable.logo)
                .into(holder.binding.image)


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