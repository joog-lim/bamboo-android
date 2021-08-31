package com.study.bamboo.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.databinding.AdminPostDeleteRecyclerItemBinding
import com.study.bamboo.utils.Admin
import com.study.bamboo.view.fragment.admin.AdminMainFragmentDirections

class AdminDeleteAdapter :
    PagingDataAdapter<Admin.Delete, AdminDeleteAdapter.AdminDeleteItemViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Admin.Delete>() {
            override fun areItemsTheSame(
                oldItem: Admin.Delete,
                newItem: Admin.Delete
            ): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: Admin.Delete,
                newItem: Admin.Delete
            ): Boolean =
                oldItem.content == newItem.content


        }
    }

    override fun onBindViewHolder(holder: AdminDeleteItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {

            holder.bind(item)
            holder.binding.postMore.setOnClickListener {
                val action =
                    AdminMainFragmentDirections.actionAdminMainFragmentToDeleteDialog(
                        item.id,holder.bindingAdapterPosition
                    )
                it.findNavController().navigateUp()
                it.findNavController().navigate(action)
            }
        }

    }
//    fun updateStatus(
//        position: Int,
//        status: String
//    ) {
//        snapshot()[position]?.status = status
//    }
//
//    fun deletePost(
//        position: Int
//    ) {
//        snapshot().toMutableList().apply { removeAt(position) }
//        notifyItemChanged(position)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDeleteItemViewHolder {
        return AdminDeleteItemViewHolder.from(parent)
    }

    //삭제
    class AdminDeleteItemViewHolder(val binding: AdminPostDeleteRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Admin.Delete) {
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AdminDeleteItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostDeleteRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_delete_recycler_item,
                        parent, false
                    )
                return AdminDeleteItemViewHolder(binding)
            }
        }
    }
}

