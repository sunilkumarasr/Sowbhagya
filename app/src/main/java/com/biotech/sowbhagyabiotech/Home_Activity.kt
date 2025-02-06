package com.biotech.sowbhagyabiotech

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.biotech.sowbhagyabiotech.base.BaseActivity
import com.biotech.sowbhagyabiotech.databinding.ActivityHomeBinding
import com.biotech.sowbhagyabiotech.roomdb.CartViewModel
import com.biotech.sowbhagyabiotech.utils.ConstantUtils

class Home_Activity : BaseActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityMainAppBar.toolbar)
        ConstantUtils.changeNotificationBarColor(this, ContextCompat.getColor(this, R.color.tab_hilet_color), false)
        navView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_home)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_category,
                R.id.navigation_cart,
                R.id.navigation_account,
                R.id.navigation_getadresslist,
                R.id.navigation_myaddress,
            )
        )
        setBadge(20)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> navController.navigate(R.id.navigation_home)
                R.id.navigation_search -> navController.navigate(R.id.navigation_search)
                R.id.navigation_category -> navController.navigate(R.id.navigation_category)
                R.id.navigation_cart -> navController.navigate(R.id.navigation_cart)
                R.id.navigation_account -> navController.navigate(R.id.navigation_account)
            }
            return@setOnItemSelectedListener true
        }

        val cartVM = CartViewModel(this, false)
        cartVM.cartData()
        cartVM.getCartItems.observe(this) {
            setBadge(it.size)
        }
        binding.activityMainAppBar.ivWhatsApp.setOnClickListener { val uri = Uri.parse(applicationContext.getString(R.string.whatasapp))
            val i = Intent(Intent.ACTION_SENDTO, uri)
            i.setPackage("com.whatsapp")
            startActivity(Intent.createChooser(i, "")) }

        binding.navView.setItemIconTintList(null);

//        if (intent.getBooleanExtra("navigate_to_cart", false)) {
//            navController = findNavController(R.id.nav_host_fragment_activity_home)
//            navController.navigate(R.id.navigation_home)
//        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        //setupBadge();
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.navigation_notification -> {
                navController.navigate(R.id.action_nav_home_to_nav_notification)
                return true
            }

            R.id.navigation_cart -> {
                navController.navigate(R.id.navigation_cart)
                return true
            }

            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack()
                onBackPressed()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    fun setHeaderTitleVisible(title: String) {
        binding.activityMainAppBar.mobileHeader.visibility = View.GONE
        binding.activityMainAppBar.mobileHeader.text = "$title"
    }

    fun setHeaderTitleGone() {
        binding.activityMainAppBar.mobileHeader.visibility = View.GONE
    }

    fun setBadge(count: Int) {
        if (count == 0) {
            navView.removeBadge(R.id.navigation_cart)
        } else {
            val badge = navView.getOrCreateBadge(R.id.navigation_cart) // previously showBadge
            badge.number = count
            badge.backgroundColor = getColor(R.color.holo_red)
            badge.badgeTextColor = getColor(R.color.white)
        }
    }

}