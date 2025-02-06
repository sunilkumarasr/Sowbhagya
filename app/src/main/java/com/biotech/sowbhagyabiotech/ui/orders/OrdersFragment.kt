package com.biotech.sowbhagyabiotech.ui.orders

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.databinding.OrdersfragmentBinding
import com.biotech.sowbhagyabiotech.ui.adapter.AdapterViewPager

class OrdersFragment : Fragment() {

    private var _binding: OrdersfragmentBinding? = null

    lateinit var home_activity: Home_Activity

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = OrdersfragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fragmentList = arrayListOf<Fragment>(
            ActiveOrdersFragment(),
            PastOrdersFragment()

        )

        val adapter = AdapterViewPager(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.tabViewpager.adapter = adapter

        TabLayoutMediator(binding.tabTablayout, binding.tabViewpager) { tab, position ->
            when (position + 1) {
                1 -> {
                    tab.text = "Active Orders"

                }

                2 -> {
                    tab.text = "Past Orders"
                }

            }
        }.attach()

        binding.tabTablayout.setSelectedTabIndicatorColor(Color.parseColor("#757575"))

        binding.tabTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                binding.tabViewpager.currentItem = tab!!.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}