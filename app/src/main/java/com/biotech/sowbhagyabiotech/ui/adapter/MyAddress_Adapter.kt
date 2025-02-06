package com.biotech.sowbhagyabiotech.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biotech.sowbhagyabiotech.databinding.DefaultaddressAdapterBinding
import com.biotech.sowbhagyabiotech.models.AddressListResponse
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper

class MyAddress_Adapter(
    var context: Context,
    var categoryList: java.util.ArrayList<AddressListResponse>,
    private var categoryClickEventListener: MoreClickEventListener

) : RecyclerView.Adapter<MyAddress_Adapter.ViewHolder>() {
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
init {
    securedSharedPreferenceWrapper =
        SecuredSharedPreferenceWrapper(context, "sowbhagya")
}

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyAddress_Adapter.ViewHolder {

        val binding = DefaultaddressAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(
            binding
        )

    }
    fun addList(news: ArrayList<AddressListResponse>) {
        categoryList = news
        notifyDataSetChanged()

    }
    inner class ViewHolder(private val binding: DefaultaddressAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: AddressListResponse) {

            binding.editaddress.text = item.fullName
            binding.mobilenumber.text = item.mobileNumber
            binding.completeadress.text =
                item.address + "," + item.streetAddress + "," + item.apartment + "," + item.city + "," + item.state + "," + item.pincode


            binding.useaddressText.visibility = View.GONE

            binding.deleteAddress.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Alert")
                builder.setMessage("Are you sure want to delete?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    item.addressId?.let { it1 -> categoryClickEventListener.deleteAddress(it1) }
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->

                    dialog.dismiss()
                }


                builder.show()

            }
            binding.edidadress.setOnClickListener {

               /* securedSharedPreferenceWrapper.putString(
                    "fullname",
                    item.fullName
                )
                securedSharedPreferenceWrapper.putString(
                    "mnum",
                    item.mobileNumber
                )
                securedSharedPreferenceWrapper.putString("hn", item.apartment)
                securedSharedPreferenceWrapper.putString(
                    "lmark",
                    item.streetAddress
                )
                securedSharedPreferenceWrapper.putString("loc", item.address)
                securedSharedPreferenceWrapper.putString("cit", item.city)
                securedSharedPreferenceWrapper.putString("stat", item.state)
                securedSharedPreferenceWrapper.putString("pin", item.pincode)
                securedSharedPreferenceWrapper.putString("address_id", item.addressId)
                securedSharedPreferenceWrapper.putInt(
                    "id",
                    item.addressId!!.toInt()
                )*/
                securedSharedPreferenceWrapper.putString("edit_fullname", item.fullName)
                securedSharedPreferenceWrapper.putString("edit_mnum", item.mobileNumber)
                securedSharedPreferenceWrapper.putString("edit_hn", item.apartment)
                securedSharedPreferenceWrapper.putString("edit_lmark",  item.streetAddress)
                securedSharedPreferenceWrapper.putString("edit_loc", item.address)
                securedSharedPreferenceWrapper.putString("edit_cit", item.city)
                securedSharedPreferenceWrapper.putString("edit_stat", item.state)
                securedSharedPreferenceWrapper.putString("edit_pin", item.pincode)
                securedSharedPreferenceWrapper.putInt("edit_id", item.addressId!!.toInt())
                categoryClickEventListener.categoryItemOnClick(item)

            }


        }
    }

    override fun onBindViewHolder(holder: MyAddress_Adapter.ViewHolder, position: Int) {
        holder.bindData(categoryList[position])
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return categoryList.size
    }


    private fun removeItem(position: Int) {
        categoryList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, categoryList.size)
    }

    interface MoreClickEventListener {
        fun categoryItemOnClick(item: AddressListResponse)
        fun deleteAddress(id: String)
    }
}
