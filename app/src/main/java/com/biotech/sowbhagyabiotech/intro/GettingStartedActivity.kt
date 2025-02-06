package com.biotech.sowbhagyabiotech.intro


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.authentication.LoginActivity
import com.biotech.sowbhagyabiotech.databinding.ActivityIntro1Binding
import java.util.Timer
import java.util.TimerTask

@SuppressLint("CustomSplashScreen")
class GettingStartedActivity : Activity() {
    private lateinit var binding: ActivityIntro1Binding
    var images: ArrayList<Int> =
        arrayListOf(R.drawable.intro_banner, R.drawable.introbanner2, R.drawable.intro_banner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            Log.e(ContentValues.TAG, "onResponse: $images")
            images.map {

                binding.viewPagerMain.adapter =

                    AdapterViewPager1(
                        this, images)

                NUM_PAGES = images.size

            }
            swipeTimer = Timer()

            // Auto start of viewpager
            val handler = Handler()
            val Update = Runnable {
                try {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0
                    }
                    binding.viewPagerMain.setCurrentItem(currentPage++, true)
                    binding.dotsIndicator.attachTo(binding.viewPagerMain)
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }

            }

            swipeTimer?.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(Update)
                }
            }, 5000, 5000)
//
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        binding.skiptxt.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.nexttxt.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    companion object {

        private var currentPage = 0
        private var NUM_PAGES = 0
        var swipeTimer: Timer? = null
    }

}