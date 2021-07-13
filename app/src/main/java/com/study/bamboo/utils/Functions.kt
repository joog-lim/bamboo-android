package com.study.bamboo.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
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

/*    fun getStandardSize() {
        val ScreenSize = getScreenSize(this)
        density = getResources().getDisplayMetrics().density
        standardSize_X = (ScreenSize!!.x / density) as Int
        standardSize_Y = (ScreenSize.y / density) as Int
    }*/
}