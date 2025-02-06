package com.biotech.sowbhagyabiotech.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biotech.sowbhagyabiotech.databinding.RowItemNotificationBinding
import com.biotech.sowbhagyabiotech.models.NotificationsResponseModel


class NotificationListAdapter: RecyclerView.Adapter<NotificationListAdapter.ViewHolder>(

) {
    private var values: ArrayList<NotificationsResponseModel> = ArrayList()
    private lateinit var clickEventListener: MoreClickEventListener

    fun addList(notificationsList: ArrayList<NotificationsResponseModel>) {
        values = notificationsList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RowItemNotificationBinding.inflate(
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


    inner class ViewHolder(private val binding: RowItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: NotificationsResponseModel) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text =  item.description
            binding.tvTime.text =  item.time
        }
    }


    interface MoreClickEventListener {
        fun onAddToCartClicked(languageList: NotificationsResponseModel?, cartQty: String?)
    }
}
