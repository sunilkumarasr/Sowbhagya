package com.biotech.sowbhagyabiotech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.models.BannersResponseModel

class ImageViewPagerAdapter(
    private val images: List<BannersResponseModel>,
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photoessadapter, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ImageViewHolder(view)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(ApiConstants.IMAGEBANNERURL+images[position].slider_image)
            .fitCenter()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

}