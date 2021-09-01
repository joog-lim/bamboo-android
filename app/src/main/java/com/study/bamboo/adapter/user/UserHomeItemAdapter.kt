package com.study.bamboo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.data.network.models.user.UserGetPostDTO
import com.study.bamboo.databinding.UserPostRecyclerItemBinding
import com.study.bamboo.data.network.models.user.UserPostDTO
import com.study.bamboo.utils.Admin
import org.w3c.dom.CharacterData
import java.text.SimpleDateFormat
import java.util.*

class UserHomeItemAdapter(
    val getPosts: LiveData<List<UserPostDTO>?>,
    val count : Int
) :
    PagingDataAdapter<UserPostDTO, UserHomeItemAdapter.UserHomeItemViewHolder>(DiffUtilCallBack()) {
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


    override fun getItemCount(): Int {
        Log.d("로그","어뎁터 안의서의 count 값 : $count")
        return count
    }


    override fun onBindViewHolder(holder: UserHomeItemViewHolder, position: Int) {
        (holder as? UserHomeItemViewHolder)?.bind(getPosts.value?.getOrNull(position) ?: return)
    }

    class UserHomeItemViewHolder(val binding: UserPostRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserPostDTO) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<UserPostDTO>() {
        override fun areItemsTheSame(oldItem: UserPostDTO, newItem: UserPostDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserPostDTO, newItem: UserPostDTO): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

