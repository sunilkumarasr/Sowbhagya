package com.biotech.sowbhagyabiotech.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.authentication.LoginViewModel
import com.biotech.sowbhagyabiotech.models.TermsAndConditionsResponse
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.ConstantUtils

class TermsAndConditionsActivity : AppCompatActivity() {


    private lateinit var shippingpolicyModel: LoginViewModel


    private lateinit var ivClose: ImageView
    private lateinit var tvNote: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)
        ConstantUtils.changeNotificationBarColor(
            this,
            ContextCompat.getColor(this, R.color.tab_hilet_color),
            false
        )
        supportActionBar?.hide()

        ivClose = findViewById(R.id.ivClose)
        tvNote = findViewById(R.id.tvNote)

        shippingpolicyModel = ViewModelProvider(this)[LoginViewModel::class.java]

        shippingpolicyModel.termsconditionsApi(this)
        CommonMethods.showProgress(this@TermsAndConditionsActivity)

        addObservers()

        ivClose.setOnClickListener {
            finish()
        }



    }

    private fun addObservers() {

        shippingpolicyModel.termsconditionsA.observe(this) {
            CommonMethods.hideProgress()
            if (it.Status == true) {
                setData(it.Response)
            }
        }
    }

    private fun setData(dt: List<TermsAndConditionsResponse>?) {

        if (dt != null) {
            if (dt.size>0){
                tvNote.setText(Html.fromHtml(dt.get(0).terms_conditions));
            }
        }
    }

}