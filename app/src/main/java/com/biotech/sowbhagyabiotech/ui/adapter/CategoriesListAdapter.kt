package com.biotech.sowbhagyabiotech.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sowbhagyabiotech.databinding.CategoryItemBinding
import com.biotech.sowbhagyabiotech.models.CategoriesResponseModel


class CategoriesListAdapter : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    private var values: ArrayList<CategoriesResponseModel> = ArrayList()
    private lateinit var categoryClickEventListener: MoreClickEventListener

    fun addList(news: ArrayList<CategoriesResponseModel>) {
        values = news
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(
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

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: CategoriesResponseModel) {

            Glide.with(binding.ivProduct.context).load(item.categoryImage)
                .into(binding.ivProduct)

            binding.tvProduct.text = item.categoryName

            binding.root.setOnClickListener {
                categoryClickEventListener.categoryItemOnClick(item)
            }
        }
    }

    fun addCategoryListener(mCategoryClickEventListener: MoreClickEventListener) {
        categoryClickEventListener = mCategoryClickEventListener
    }



    interface MoreClickEventListener {
        fun categoryItemOnClick(item: CategoriesResponseModel)
    }
}
