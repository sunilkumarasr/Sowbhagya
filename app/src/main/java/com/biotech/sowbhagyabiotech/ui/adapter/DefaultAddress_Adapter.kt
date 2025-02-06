package com.biotech.sowbhagyabiotech.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.models.AddressListResponse
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper

class DefaultAddress_Adapter(
    var context: Context,
    val categoryList: java.util.ArrayList<AddressListResponse>
) :
    RecyclerView.Adapter<DefaultAddress_Adapter.ViewHolder>() {

    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

//    var subcat_id : String =""

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(context, "sowbhagya")

        val v =
            LayoutInflater.from(context).inflate(R.layout.defaultaddress_adapter, parent, false)

        return ViewHolder(v)
    }

    //this method is binding the data on the list
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.username.text = categoryList.get(position).fullName
        holder.mobilenumber.text = categoryList.get(position).mobileNumber
        holder.location.text =
            categoryList.get(position).address + "," + categoryList.get(position).streetAddress + "," + categoryList.get(
                position
            ).apartment + "," + categoryList.get(position).city + "," + categoryList.get(position).state + "," + categoryList.get(
                position
            ).pincode



        holder.useaddress.setOnClickListener {

            val id = categoryList.get(position).addressId
            val name = categoryList.get(position).fullName
            val number = categoryList.get(position).mobileNumber
            val housenumber = categoryList.get(position).apartment
            val land = categoryList.get(position).streetAddress
            val loc = categoryList.get(position).address
            val city = categoryList.get(position).city
            val stat = categoryList.get(position).state
            val pin = categoryList.get(position).pincode

            securedSharedPreferenceWrapper.putString("fullname", name)
            securedSharedPreferenceWrapper.putString("mnum", number)
            securedSharedPreferenceWrapper.putString("hn", housenumber)
            securedSharedPreferenceWrapper.putString("lmark", land)
            securedSharedPreferenceWrapper.putString("loc", loc)
            securedSharedPreferenceWrapper.putString("cit", city)
            securedSharedPreferenceWrapper.putString("stat", stat)
            securedSharedPreferenceWrapper.putString("pin", pin)
            securedSharedPreferenceWrapper.putInt("id", id!!.toInt())
            val navController =
                Navigation.findNavController(
                    context as Activity,
                    R.id.nav_host_fragment_activity_home
                )
            navController.navigate(R.id.navigation_checkout)


        }
        holder.edidadress.setOnClickListener {

            val id = categoryList.get(position).addressId
            val name = categoryList.get(position).fullName
            val number = categoryList.get(position).mobileNumber
            val housenumber = categoryList.get(position).apartment
            val land = categoryList.get(position).address
            val loc = categoryList.get(position).streetAddress
            val city = categoryList.get(position).city
            val stat = categoryList.get(position).state
            val pin = categoryList.get(position).pincode

            securedSharedPreferenceWrapper.putString("edit_fullname", name)
            securedSharedPreferenceWrapper.putString("edit_mnum", number)
            securedSharedPreferenceWrapper.putString("edit_hn", housenumber)
            securedSharedPreferenceWrapper.putString("edit_lmark", land)
            securedSharedPreferenceWrapper.putString("edit_loc", loc)
            securedSharedPreferenceWrapper.putString("edit_cit", city)
            securedSharedPreferenceWrapper.putString("edit_stat", stat)
            securedSharedPreferenceWrapper.putString("edit_pin", pin)
            securedSharedPreferenceWrapper.putInt("edit_id", id!!.toInt())


            val navController =
                Navigation.findNavController(
                    context as Activity,
                    R.id.nav_host_fragment_activity_home
                )
            navController.navigate(R.id.navigation_addadress)

        }


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return categoryList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val username = itemView.findViewById(R.id.editaddress) as TextView
        val mobilenumber = itemView.findViewById(R.id.mobilenumber) as TextView
        val location = itemView.findViewById(R.id.completeadress) as TextView
        val edidadress = itemView.findViewById(R.id.edidadress) as ImageView
        val useaddress = itemView.findViewById(R.id.useaddress_text) as TextView


    }

    private fun removeItem(position: Int) {
        categoryList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, categoryList.size)
    }


}
