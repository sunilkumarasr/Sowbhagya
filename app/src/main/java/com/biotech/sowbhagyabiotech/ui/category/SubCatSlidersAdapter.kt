package com.biotech.sowbhagyabiotech.ui.category

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.models.SubCatProductsResponseModel


class SubCatSlidersAdapter(
    private val context: Context,
    private val imageModelArrayList: ArrayList<SubCatProductsResponseModel>
) : PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.subcategory_item, view, false)!!

        val iv_product = imageLayout.findViewById(R.id.iv_product) as ImageView
        val productName = imageLayout.findViewById(R.id.productName) as TextView
        val tv_price = imageLayout.findViewById(R.id.tv_price) as TextView
        val addtocartBT = imageLayout.findViewById(R.id.addtocartBT) as Button

     /*   imageLayout.setOnClickListener {
            sliderClickEvent.onSliderClick(item = imageModelArrayList[position])
        }*/
//        imageView.setBackgroundResource(imageModelArrayList.get(position).getImage_drawables())

        Glide.with(context).load(imageModelArrayList.get(position).productImage)
            .apply(RequestOptions().centerCrop())
            .transform(CenterCrop(), RoundedCorners(15))

            .error(R.drawable.logo)
            .into(iv_product)
        view.addView(imageLayout, 0)


        productName.text = imageModelArrayList.get(position).productName
        tv_price.text = "Unit Price : Rs. " + imageModelArrayList.get(position).offerPrice


        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }


}

interface SliderClickEvent {
    fun onSliderClick(item: SubCatProductsResponseModel)
}


