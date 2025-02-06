package com.biotech.sowbhagyabiotech.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.WindowManager
import android.widget.LinearLayout
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.retrofit.WSClient

object CommonMethods {


    private var mProgressDialog: ProgressDialog? = null
    @SuppressLint("ResourceAsColor")
    fun showProgress(mContext: Context) {
        if (mProgressDialog == null) {

            mProgressDialog = ProgressDialog(mContext, R.style.Theme_SowbhagyaBiotech)
            if (mProgressDialog?.window != null) mProgressDialog?.window!!
                .setBackgroundDrawable(
                    ColorDrawable(Color.parseColor("#7F000000"))
                )
//            val dialog = builder.create()
//            val window = dialog.window
//            if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
//                layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
            layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
//                dialog.window?.attributes = layoutParams
//            }
            mProgressDialog?.window?.attributes = layoutParams

//            mProgressDialog?.window?.setFlags(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT
//            )
            mProgressDialog?.isIndeterminate = true
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.setCanceledOnTouchOutside(false)
            mProgressDialog?.show()
            mProgressDialog?.setContentView(R.layout.layout_loader_progress_bar)

            mProgressDialog?.setOnDismissListener {
            }
        }

        if (!mProgressDialog!!.isShowing) {
            mProgressDialog?.show()
        }

    }

    fun hideProgress() {

        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog?.dismiss()
            WSClient.requestQue?.clear()
        }
        mProgressDialog = null
    }


    fun checkInternetConnection(mActivity: Context): Boolean {
        return isInternetAvailable(mActivity)
    }

    private fun isInternetAvailable(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        val result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result


    }
}

fun String?.nullCheck():String{
    return if(this?.trim().isNullOrEmpty() || this.equals("null",true)){
        ""
    }else{
        this!!
    }

}