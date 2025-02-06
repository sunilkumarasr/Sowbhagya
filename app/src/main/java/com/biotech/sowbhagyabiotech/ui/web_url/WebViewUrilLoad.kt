package com.biotech.sowbhagyabiotech.ui.web_url

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.databinding.ActivityWebViewUrilLoadBinding
import com.biotech.sowbhagyabiotech.utils.ConstantUtils

class WebViewUrilLoad : AppCompatActivity() {
    lateinit var binding: ActivityWebViewUrilLoadBinding
    var urlType: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewUrilLoadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ConstantUtils.changeNotificationBarColor(
            this,
            ContextCompat.getColor(this, R.color.color_primary),
            false
        )
        val intent = intent

        if (intent.hasExtra("url_type")) {
            urlType = intent.extras?.getString("url_type").toString()

        } else {
            urlType = ""
        }

        binding.ivClose.setOnClickListener { finish() }
        binding.wvCommon?.webViewClient = WebViewClient()

        /*viewModel.getAboutTermsPrivacyLiveData.observe(this) {
            binding.tvPageTitle.text = it?.data?.page_title

            Glide.with(this)
                .asBitmap()
                .load(it?.data?.page_image)
                .centerCrop()
                .into(binding.ivHeader);
            it?.data?.information_title?.let { it1 -> binding.wvCommon?.loadData(it1, "text/html", "UTF-8") }
        }*/


        if(urlType == ApiConstants.TERMS_CONDITIONS_TAG){
            binding.wvCommon?.loadUrl(ApiConstants.TERMS_CONDITIONS)
        }else if(urlType == ApiConstants.PRIVACY_POLICY_TAG){
            binding.wvCommon?.loadUrl(ApiConstants.PRIVACY_POLICY)
        }else if(urlType == ApiConstants.SHIPPING_TAG){
            binding.wvCommon?.loadUrl(ApiConstants.SHIPPING)
        }else if(urlType == ApiConstants.RETURN_TAG){
            binding.wvCommon?.loadUrl(ApiConstants.RETURN)
        }


        binding.wvCommon?.settings?.javaScriptEnabled = true
        binding.wvCommon?.settings?.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (binding.wvCommon?.canGoBack()!!)
            binding.wvCommon?.goBack()
        else
            super.onBackPressed()
    }


}