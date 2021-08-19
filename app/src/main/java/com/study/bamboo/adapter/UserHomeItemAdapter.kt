package com.study.bamboo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.databinding.UserPostRecyclerItemBinding
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

        val binding = DataBindingUtil.inflate<UserPostRecyclerItemBinding>(LayoutInflater.from(parent.context), R.layout.user_post_recycler_item, parent, false)
        return UserHomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holderUser: UserHomeItemViewHolder, position: Int) {
        (holderUser as? UserHomeItemViewHolder)?.bind(getPosts.value?.getOrNull(position) ?: return)
    }

    override fun getItemCount(): Int {
        return getPosts.value!!.size
    }

}

class UserHomeItemViewHolder(val binding: UserPostRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data : UserPostDTO){
        binding.data = data
        binding.executePendingBindings()
    }
}