package com.example.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.admin.response.PostEntity
import com.example.presentation.R
import com.example.presentation.databinding.UserPostRecyclerItemBinding
class UserHomeItemAdapter(


) :
    PagingDataAdapter<PostEntity, UserHomeItemAdapter.UserHomeItemViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHomeItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<UserPostRecyclerItemBinding>(
            layoutInflater,
            R.layout.user_post_recycler_item,
            parent,
            false
        )
        return UserHomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHomeItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)


        }


    }

    class UserHomeItemViewHolder(val binding: UserPostRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PostEntity) {
            Log.d("로그", "userHomeItemAdapter bind data : $data")
            binding.data = data
            binding.executePendingBindings()
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<PostEntity>() {
        override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
            return oldItem == newItem
        }
    }


}