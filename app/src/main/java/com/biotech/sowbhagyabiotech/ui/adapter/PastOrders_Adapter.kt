package com.biotech.sowbhagyabiotech.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.models.PastOrders

class PastOrders_Adapter(
    var context: Context,
    var categoryproductList: List<PastOrders>
) :
    RecyclerView.Adapter<PastOrders_Adapter.ViewHolder>() {


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v =
            LayoutInflater.from(context).inflate(R.layout.ordersadapter, parent, false)
        return ViewHolder(v)
    }

    fun addList(news: ArrayList<PastOrders>) {
        categoryproductList = news
        notifyDataSetChanged()

    }

    //this method is binding the data on the list
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (categoryproductList.get(position).deliveryStatus=="0"){
            holder.orderStatus.text = "Confirmed"
            holder.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.green))
        }
        if (categoryproductList.get(position).deliveryStatus=="2"){
            holder.orderStatus.text = "Packed"
        }
        if (categoryproductList.get(position).deliveryStatus=="3"){
            holder.orderStatus.text = "Shipped"
        }
        if (categoryproductList.get(position).deliveryStatus=="4"){
            holder.orderStatus.text = "Delivered"
        }
        if (categoryproductList.get(position).deliveryStatus=="5"){
            holder.orderStatus.text = "Cancelled"
            holder.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.holo_red))
        }

        holder.ordernumber.text = categoryproductList.get(position).orderNumber
        holder.amount.text = "\u20b9 " + categoryproductList.get(position).grandTotal + ""
        holder.order_date.text = categoryproductList.get(position).orderDate
        val ad = Orders_Sub_Adapter(context, categoryproductList.get(position).productDetails)
        holder.rec.adapter = ad

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return categoryproductList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderStatus = itemView.findViewById(R.id.orderStatus) as TextView
        val ordernumber = itemView.findViewById(R.id.ordernumber) as TextView
        val amount = itemView.findViewById(R.id.amount) as TextView
        val order_date = itemView.findViewById(R.id.date_text) as TextView
        val rec = itemView.findViewById(R.id.dataRCID) as RecyclerView

    }

}
