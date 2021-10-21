package com.study.bamboo.adapter.user

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.data.network.models.user.UserPostDTO
import com.study.bamboo.databinding.UserPostRecyclerItemBinding
import com.study.bamboo.view.fragment.user.UserMainFragmentDirections
import com.study.bamboo.view.fragment.user.viewmodel.UserViewModel

class UserHomeItemAdapter(
    private val viewModel: UserViewModel
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

    override fun onBindViewHolder(holder: UserHomeItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
        var good = false
        var sad = false

        holder.binding.declarationBtn.setOnClickListener {
            val direction: NavDirections =
                UserMainFragmentDirections.actionUserMainFragmentToDeclarationFragment(item!!.id)
            it.findNavController().navigate(direction)
        }
        holder.binding.goodEmoji.setOnClickListener {

            good = if (good) {

                holder.binding.goodEmoji.setBackgroundResource(R.drawable.ic_good_no_img)
                viewModel.postEmoji("")
                false


            } else {

                holder.binding.goodEmoji.setBackgroundResource(R.drawable.ic_good_img)
                viewModel.postEmoji("")
                true
            }
        }


        holder.binding.sadEmoji.setOnClickListener {
            sad = if (sad) {


                holder.binding.goodEmoji.setBackgroundResource(R.drawable.ic_sad_no_img)
                viewModel.deleteEmoji("")
                false
            } else {

                holder.binding.sadEmoji.setBackgroundResource(R.drawable.ic_sad_img)
                viewModel.postEmoji("")
                true

            }

        }
    }

    class UserHomeItemViewHolder(val binding: UserPostRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserPostDTO) {
            Log.d("로그", "userHomeItemAdapter bind data : $data")
            binding.data = data
            binding.executePendingBindings()
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<UserPostDTO>() {
        override fun areItemsTheSame(oldItem: UserPostDTO, newItem: UserPostDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserPostDTO, newItem: UserPostDTO): Boolean {
            return oldItem == newItem
        }
    }
}