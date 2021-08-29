package com.study.bamboo.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.databinding.AdminPostRejectRecyclerItemBinding
import com.study.bamboo.utils.Admin
import com.study.bamboo.view.fragment.admin.AdminMainFragmentDirections

class AdminRejectAdapter :
    PagingDataAdapter<Admin.Reject, AdminRejectAdapter.AdminRejectItemViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Admin.Reject>() {
            override fun areItemsTheSame(
                oldItem: Admin.Reject,
                newItem: Admin.Reject
            ): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: Admin.Reject,
                newItem: Admin.Reject
            ): Boolean =
                oldItem.content == newItem.content


        }
    }

    //거부
    class AdminRejectItemViewHolder(val binding: AdminPostRejectRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Admin.Reject) {
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AdminRejectItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostRejectRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_reject_recycler_item,
                        parent, false
                    )
                return AdminRejectItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: AdminRejectItemViewHolder, position: Int) {
        val item=getItem(position)
        if(item!=null) {
            holder.bind(item)
            holder.binding.postMore.setOnClickListener {
                val action =
                    AdminMainFragmentDirections.actionAdminMainFragmentToRejectCancelDialog(
                        item.id
                    )
                it.findNavController().navigateUp()
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminRejectItemViewHolder {
        return AdminRejectItemViewHolder.from(parent)
    }
}