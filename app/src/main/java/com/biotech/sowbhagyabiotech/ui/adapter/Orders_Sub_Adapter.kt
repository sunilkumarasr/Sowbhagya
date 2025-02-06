package com.biotech.sowbhagyabiotech.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.models.ProductDetails

class Orders_Sub_Adapter(
    var context: Context,
    val categoryproductList: List<ProductDetails>
) :
    RecyclerView.Adapter<Orders_Sub_Adapter.ViewHolder>() {

//    var subcat_id : String =""

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v =
            LayoutInflater.from(context).inflate(R.layout.ordersubadapter, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.productname.text = categoryproductList.get(position).productName
        holder.amount.text = "\u20b9 " + categoryproductList.get(position).price.toString()
        holder.quantity.text = "Qty :" + categoryproductList.get(position).qty

        Glide.with(context).load(ApiConstants.IMAGEPRODUCTSURL+categoryproductList.get(position).image)
            .error(R.drawable.logo)
            .into(holder.pimage)


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return categoryproductList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productname = itemView.findViewById(R.id.productname) as TextView
        val amount = itemView.findViewById(R.id.price) as TextView
        val quantity = itemView.findViewById(R.id.quantity) as TextView
        val pimage = itemView.findViewById(R.id.productimage) as ImageView

    }

}
