package com.biotech.sowbhagyabiotech.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.databinding.FragmentNotificationsBinding
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.ui.adapter.NotificationListAdapter
import com.biotech.sowbhagyabiotech.utils.CommonMethods

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationListAdapter: NotificationListAdapter
    private lateinit var notificationsViewModel: NotificationsViewModel
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")

        this.notificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]

//        addObservers()
//        setUpRecyclerView()
//        notificationsViewModel.callNotificationsApi(
//            requireContext(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
//        )




        return root
    }

    private fun setUpRecyclerView() {
        val ll = GridLayoutManager(
            requireActivity(), 1
        )
        binding.rcvNotifications.layoutManager = ll
        notificationListAdapter = NotificationListAdapter()
        binding.rcvNotifications.adapter = notificationListAdapter


    }


    private fun addObservers() {

        notificationsViewModel.notificationResponseModel.observe(requireActivity()) {
            if (it?.Status == true) {
                CommonMethods.hideProgress()
                Log.i("data_GOT", "data = ${it.Response}")
                if (it.Response.isEmpty()){
                    binding.noDataFoundTV.visibility=View.VISIBLE
                    binding.rcvNotifications.visibility=View.GONE
                }else{
                    binding.noDataFoundTV.visibility=View.GONE
                    binding.rcvNotifications.visibility=View.VISIBLE
                    notificationListAdapter.addList(it.Response)

                }

            } else {
                CommonMethods.hideProgress()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("Notifications")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()
    }
}