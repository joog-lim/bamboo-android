package com.study.bamboo.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


object Functions {
    //리사이클러뷰
    fun recyclerViewManager(v: RecyclerView, context: Context) {
        v.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun getScreenSize(activity: Activity): Point? {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }
}