package com.study.bamboo.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object Functions {
    //리사이클러뷰
    fun recyclerViewManager(v: RecyclerView, context: Context) {
        v.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}