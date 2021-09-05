package com.study.bamboo.bindingadapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object PostBindingAdapter {

    //admin


    @BindingAdapter("app:day")
    @JvmStatic
    fun getDay(text: TextView, day: Long) {

        val toTimeStamp = Date(day)
        val date = SimpleDateFormat("yyyy년 MM월 dd일 aa", Locale.forLanguageTag("ko"))
        text.text = date.format(toTimeStamp)

    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("app:number", "app:status")
    @JvmStatic
    fun getNumber(text: TextView, number: Int, status: String) {

        when (status) {

            "ACCEPTED" -> text.text = "#${number}번째 알고리즘"
            "DELETED" -> text.text = "#${number}번째 삭제요청"
            "REJECTED" -> text.text = "#${number}번째 거절됨"
            "PENDING" -> text.text = "#${number}번째 대기중"
        }


    }

    @BindingAdapter("app:chooseStatus")
    @JvmStatic
    fun getStatus(text: TextView, status: String) {

        when (status) {

            "ACCEPTED" -> text.text = "수정"
            "DELETED" -> text.text = "더보기"
            "REJECTED" -> text.text = "거절취소"
            "PENDING" -> text.text = "더보기"
        }


    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("app:getTag")
    @JvmStatic
    fun getTag(text: TextView, tag: String) {

        text.text = "#${tag}"


    }


    //user


    @BindingAdapter("app:userDay")
    @JvmStatic
    fun getUserDay(text: TextView, day: Long) {

        val toTimeStamp = Date(day)
        val date = SimpleDateFormat("yyyy년 MM월 dd일 aa", Locale.forLanguageTag("ko"))
        text.text = date.format(toTimeStamp)
    }

    @BindingAdapter("app:userPostNumber")
    @JvmStatic
    fun getUserPostNumber(text: TextView, number: Int) {

        text.text = "#${number}번째 알고리즘"

    }

    @BindingAdapter("app:userPostTag")
    @JvmStatic
    fun getUserPostTag(text: TextView, tag: String) {

        text.text = tag

    }

    @BindingAdapter("app:userPostTitle")
    @JvmStatic
    fun getUserPostTitle(text: TextView, title: String) {

        text.text = title

    }

    @BindingAdapter("app:userPostContent")
    @JvmStatic
    fun getUserPostContent(text: TextView, content: String) {

        text.text = content

    }
}