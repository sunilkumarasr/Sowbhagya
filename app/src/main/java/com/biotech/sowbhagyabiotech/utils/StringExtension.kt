package com.biotech.sowbhagyabiotech.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun String.showAsToast(context: Context) {
    showAsToast(context, Toast.LENGTH_SHORT)
}

fun String?.optString(optValue: String): String {
    if (isNullOrEmpty()) {
        return optValue
    }
    return this
}

fun String.showAsToast(context: Context, duration: Int) {
    Toast.makeText(context, this, duration).show()
}

fun String.toCalendar(format: String): Calendar {
    val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    calendar.time = simpleDateFormat.parse(this)
    return calendar
}

fun Calendar.toStringFormat(format: String): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    return simpleDateFormat.format(time)
}

fun String.isValidMobile(lengthX: Int = 10): Boolean {
    return length == lengthX
}

fun String.isValidOtp(lengthX: Int = 6): Boolean {
    return length == lengthX
}

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}


fun String.getDayOfMonthSuffix(): String {
    val n = toInt()
    return if (n in 11..13) {
        "th"
    } else when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }

}


fun String.toMultiBodyFilePart(key: String, mimeType: String = ""): MultipartBody.Part {
    val file = File(this)
    val actualMimeType = mimeType.toMediaTypeOrNull()
    val fileBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(key, file.name, fileBody)
}

fun String.optInt(): Int {
    if (isNotEmpty()) {
        return toInt()
    }
    return 0
}

fun String.optDouble(): Double {
    if (isNotEmpty()) {
        return toDoubleOrNull() ?: 0.0
    }
    return 0.0
}