package com.biotech.sowbhagyabiotech.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.models.ActiveOrders

class ActiveOrders_Adapter(
    var context: Context,
    var categoryproductList: java.util.ArrayList<ActiveOrders>,
) :
    RecyclerView.Adapter<ActiveOrders_Adapter.ViewHolder>() {

    private lateinit var categoryClickEventListener: MoreClickEventListenerorderID

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v =
            LayoutInflater.from(context).inflate(R.layout.ordersadapter, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (categoryproductList.get(position).deliveryStatus=="0"){
            holder.orderStatus.text = "Confirmed"
            holder.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.green))
            holder.cardCancel.visibility = View.VISIBLE
        }
        if (categoryproductList.get(position).deliveryStatus=="2"){
            holder.orderStatus.text = "Packed"
            holder.cardCancel.visibility = View.GONE
        }
        if (categoryproductList.get(position).deliveryStatus=="3"){
            holder.orderStatus.text = "Shipped"
            holder.cardCancel.visibility = View.GONE
        }
        if (categoryproductList.get(position).deliveryStatus=="4"){
            holder.orderStatus.text = "Delivered"
            holder.cardCancel.visibility = View.GONE
        }
        if (categoryproductList.get(position).deliveryStatus=="5"){
            holder.orderStatus.text = "Cancelled"
            holder.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.holo_red))
            holder.cardCancel.visibility = View.GONE
        }


        holder.ordernumber.text = categoryproductList.get(position).orderNumber
        holder.amount.text = "\u20b9 " + categoryproductList.get(position).grandTotal
        holder.date_text.text = categoryproductList.get(position).orderDate
        val ad = Orders_Sub_Adapter(context, categoryproductList.get(position).productDetails)
        holder.rec.adapter = ad


        holder.cardCancel.setOnClickListener {
            categoryClickEventListener.categoryItemOnClick(categoryproductList.get(position).orderNumber.toString())
        }

    }

    fun addList(news: ArrayList<ActiveOrders>) {
        categoryproductList = news
        notifyDataSetChanged()

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
        val date_text = itemView.findViewById(R.id.date_text) as TextView
        val rec = itemView.findViewById(R.id.dataRCID) as RecyclerView
        val cardCancel = itemView.findViewById(R.id.cardCancel) as CardView

    }

    fun addCategoryListener(mCategoryClickEventListener: Any) {
        categoryClickEventListener = mCategoryClickEventListener as MoreClickEventListenerorderID
    }


    interface MoreClickEventListenerorderID {
        fun categoryItemOnClick(orderID: String)
    }

}
