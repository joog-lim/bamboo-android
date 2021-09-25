package com.study.bamboo.adapter.admin

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.databinding.AdminPostWaitingRecyclerItemBinding
import com.study.bamboo.utils.Admin
import com.study.bamboo.view.fragment.admin.AdminMainFragmentDirections

class AdminPendingAdapter :
    PagingDataAdapter<Admin.Pending, AdminPendingAdapter.AdminPendingItemViewHolder>(diffCallback) {
    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Admin.Pending>() {
            override fun areItemsTheSame(
                oldItem: Admin.Pending,
                newItem: Admin.Pending
            ): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: Admin.Pending,
                newItem: Admin.Pending
            ): Boolean =
                oldItem == newItem


        }
    }

    //대기
    class AdminPendingItemViewHolder(val binding: AdminPostWaitingRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Admin.Pending) {
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AdminPendingItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostWaitingRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_waiting_recycler_item,
                        parent, false
                    )
                return AdminPendingItemViewHolder(binding)
            }
        }
    }



    override fun onBindViewHolder(holder: AdminPendingItemViewHolder, position: Int) {
        val item=getItem(position)
        try {
            if (item != null) {
                holder.bind(item)
                holder.binding.postMore.setOnClickListener {
                    val action =
                        AdminMainFragmentDirections.actionAdminMainFragmentToPendingDialog(
                            item.id, holder.bindingAdapterPosition
                        )
                    it.findNavController().navigateUp()
                    it.findNavController().navigate(action)
                }
             holder.binding.postModifyText.setOnClickListener {
                 val action =
                     AdminMainFragmentDirections.actionAdminMainFragmentToAcceptDialog(
                         item.id,
                         holder.bindingAdapterPosition,
                         item.title,
                         item.content,
                         item.tag,
                         item.status
                     )
                 it.findNavController().navigateUp()
                 it.findNavController().navigate(action)
             }
            }
        }catch (e:Exception){
            Log.d("PendingAdapter", "onBindViewHolder: 네비게이션 없음 ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminPendingItemViewHolder {
        return AdminPendingItemViewHolder.from(parent)
    }
}