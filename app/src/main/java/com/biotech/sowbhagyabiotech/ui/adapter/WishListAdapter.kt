package com.biotech.sowbhagyabiotech.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sowbhagyabiotech.databinding.WishlistItemBinding
import com.biotech.sowbhagyabiotech.models.WishListResponseModel


class WishListAdapter : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    private var values: ArrayList<WishListResponseModel> = ArrayList()
    private lateinit var categoryClickEventListener: MoreClickEventListener

    fun addList(news: ArrayList<WishListResponseModel>) {
        values = news
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = WishlistItemBinding.inflate(
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

    inner class ViewHolder(private val binding: WishlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: WishListResponseModel) {

            Glide.with(binding.ivProduct.context).load(item.productImage)
                .into(binding.ivProduct)

            binding.tvProduct.text = item.productName
            binding.priceTxt.text = item.offerPrice

//            binding.root.setOnClickListener {
//                categoryClickEventListener.categoryItemOnClick(item)
//            }
        }
    }

    fun addCategoryListener(mCategoryClickEventListener: MoreClickEventListener) {
        categoryClickEventListener = mCategoryClickEventListener
    }

    interface MoreClickEventListener {
        fun categoryItemOnClick(item: WishListResponseModel)
    }
}
