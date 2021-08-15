package com.study.bamboo.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

object PostBindingAdapter {

    @BindingAdapter("app:day")
    @JvmStatic
    fun getDay(text:TextView,day:Long){

        text.text=day.toString()

    }

}