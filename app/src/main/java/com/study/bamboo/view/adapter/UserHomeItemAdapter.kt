package com.study.bamboo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.utils.ViewModel.signInViewModel
import java.text.SimpleDateFormat
import java.util.*

class UserHomeItemAdapter(val getPosts: MutableLiveData<List<UserPostDTO>?>) :
    RecyclerView.Adapter<UserHomeItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHomeItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.user_post_recycler_item, parent, false)

        return UserHomeItemViewHolder(listItem)
    }

    override fun onBindViewHolder(holderUser: UserHomeItemViewHolder, position: Int) {
        signInViewModel.getPostResponse.value?.get(position)?.let {
            Log.d("로그", "리사이클러뷰 번호 :${it.number.toString()}, 리사이클러뷰 사이즈 ${getPosts.value?.size}")

        }
        //가져온 정보 리사이클러뷰에 표현
        holderUser.view.findViewById<TextView>(R.id.user_post_number).text =
            "#${getPosts.value?.get(position)?.number.toString()}번째 알고리즘"
        holderUser.view.findViewById<TextView>(R.id.user_post_tag).text =
            "#${getPosts.value?.get(position)?.tag.toString()}"
        holderUser.view.findViewById<TextView>(R.id.user_post_md).text =
            "${getPosts.value?.get(position)?.title.toString()}"
        holderUser.view.findViewById<TextView>(R.id.user_post_con).text =
            "${getPosts.value?.get(position)?.content.toString()}"
        holderUser.view.findViewById<TextView>(R.id.user_post_time).text =
            "${toTimeStamp(getPosts.value?.get(position)?.createdAt!!.toLong())}"

    }

    override fun getItemCount(): Int {
        return getPosts.value!!.size
    }

    //타임스탬프 변환
    private fun toTimeStamp(num: Long): String? {
        val toTimeStamp = Date(num)
        val datef = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분", Locale.getDefault())
        return datef.format(toTimeStamp)
    }

}

class UserHomeItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
}