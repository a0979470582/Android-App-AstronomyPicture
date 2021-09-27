package com.bu.cmoney.tool

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.bu.cmoney.CMoneyApplication

fun hasNetwork(): Boolean {
    val connectivityManager = CMoneyApplication.context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.activeNetwork != null
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isAvailable
    }
}