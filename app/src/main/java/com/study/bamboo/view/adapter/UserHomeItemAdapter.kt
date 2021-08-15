package com.study.bamboo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.model.dto.PostDTO
import com.study.bamboo.utils.ViewModel.signInViewModel

class UserHomeItemAdapter(val getPosts: MutableLiveData<List<PostDTO>?>) : RecyclerView.Adapter<AdminHomeItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.user_post_recycler_item, parent, false)

        return AdminHomeItemViewHolder(listItem)
    }

    override fun onBindViewHolder(holderUser: AdminHomeItemViewHolder, position: Int) {
        signInViewModel.getPostResponse.value?.get(position)?.let {
            Log.d("로그","리사이클러뷰 번호 :${it.number.toString()}, 리사이클러뷰 사이즈 ${getPosts.value?.size}")

        }
        holderUser.view.findViewById<TextView>(R.id.user_post_number).text = "#${getPosts.value?.get(position)?.number.toString()}번째 알고리즘"
        holderUser.view.findViewById<TextView>(R.id.user_post_tag).text = "#${getPosts.value?.get(position)?.tag.toString()}"
        holderUser.view.findViewById<TextView>(R.id.user_post_md).text = "${getPosts.value?.get(position)?.title.toString()}"
        holderUser.view.findViewById<TextView>(R.id.user_post_con).text = "${getPosts.value?.get(position)?.content.toString()}"
    }

    override fun getItemCount(): Int {
        return getPosts.value!!.size
    }
}

class UserHomeItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
}