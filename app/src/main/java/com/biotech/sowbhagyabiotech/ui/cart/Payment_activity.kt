package com.biotech.sowbhagyabiotech.ui.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.databinding.FragmentPaymentBinding

class Payment_activity : Activity() {

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val address = intent.getStringExtra("address")
        val Gst = intent.getStringExtra("Gst")
        val ProductsPrice = intent.getStringExtra("ProductsPrice")
        val TotalPrice = intent.getStringExtra("TotalPrice")


        binding.txtAddress.text = address
        binding.totalGST.text = Gst
        binding.totalpricee.text = ProductsPrice
        binding.totalprice.text = TotalPrice


        binding.okBT.setOnClickListener {
            val intent = Intent(this, Home_Activity::class.java)
            startActivity(intent)
            finish()
        }


    }


}