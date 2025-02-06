package com.biotech.sowbhagyabiotech.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.databinding.SubcategoryItemBinding

class ProductFragment : Fragment() {
    private var _binding: SubcategoryItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SubcategoryItemBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("Categories")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }
}