package com.study.bamboo

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object PostBindingAdapter {

    //admin


    @RequiresApi(Build.VERSION_CODES.O)
    @BindingAdapter("app:day")
    @JvmStatic
    fun getDay(text: TextView, day: String) {


        val parsedDate = LocalDateTime.parse(day, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd a"))
        text.text = formattedDate
    }


    @BindingAdapter("app:imgClicked")
    @JvmStatic
    fun ImageView.leafClicked(clicked: Boolean) {

        if (clicked) {
            this.setBackgroundResource(R.drawable.click_leaf)
        } else {
            this.setBackgroundResource(R.drawable.no_click_leaf)
        }
    }

    @BindingAdapter("app:leafNumber")
    @JvmStatic
    fun TextView.leafNumber(number: Int) {
        this.text = number.toString()
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("app:getTag")
    @JvmStatic
    fun getTag(text: TextView, tag: String) {

        text.text = "#${tag}"
    }

    @BindingAdapter("app:acceptStatus")
    @JvmStatic
    fun getAcceptStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 알고리즘"
    }

    @BindingAdapter("app:deleteStatus")
    @JvmStatic
    fun getDeleteStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 삭제요청"
    }

    @BindingAdapter("app:rejectStatus")
    @JvmStatic
    fun getRejectStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 거절됨"
    }

    @BindingAdapter("app:pendingStatus")
    @JvmStatic
    fun getPendingStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 대기중"
    }


}