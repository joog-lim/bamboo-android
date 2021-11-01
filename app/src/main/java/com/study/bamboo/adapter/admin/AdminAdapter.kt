package com.study.bamboo.adapter.admin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.data.network.models.user.UserPostDTOBase
import com.study.bamboo.databinding.AdminPostAcceptedRecyclerItemBinding
import com.study.bamboo.databinding.AdminPostDeleteRecyclerItemBinding
import com.study.bamboo.databinding.AdminPostRejectRecyclerItemBinding
import com.study.bamboo.databinding.AdminPostWaitingRecyclerItemBinding
import com.study.bamboo.util.Admin
import com.study.bamboo.view.fragment.admin.AdminMainFragmentDirections

// TODO: 2021-08-16 어댑터를 나누자 각각의 상황에맞게


class AdminAdapter

    :
    PagingDataAdapter<UserPostDTOBase, RecyclerView.ViewHolder>(diffCallback) {

    companion object {

        const val TAG = "AdminHomeItemAdapter"
        const val ACCEPTEDType = 1
        const val PENDINGType = 2
        const val REJECTEDType = 3
        const val DELETEDType = 4

        const val ACCEPTED = "ACCEPTED"
        const val PENDING = "PENDING"
        const val DELETED = "DELETED"
        const val REJECTED = "REJECTED"

        private val diffCallback = object : DiffUtil.ItemCallback<UserPostDTOBase>() {
            override fun areItemsTheSame(
                oldItem: UserPostDTOBase,
                newItem: UserPostDTOBase
            ): Boolean {
                return when {
                    oldItem is Admin.Accept && newItem is Admin.Accept -> oldItem == newItem
                    oldItem is Admin.Pending && newItem is Admin.Pending -> oldItem == newItem
                    oldItem is Admin.Delete && newItem is Admin.Delete -> oldItem == newItem
                    oldItem is Admin.Reject && newItem is Admin.Reject -> oldItem == newItem
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: UserPostDTOBase,
                newItem: UserPostDTOBase
            ): Boolean {
                return when {
                    oldItem is Admin.Accept && newItem is Admin.Accept -> oldItem.id == newItem.id
                    oldItem is Admin.Pending && newItem is Admin.Pending -> oldItem.id == newItem.id
                    oldItem is Admin.Delete && newItem is Admin.Delete -> oldItem.id == newItem.id
                    oldItem is Admin.Reject && newItem is Admin.Reject -> oldItem.id == newItem.id
                    else -> false
                }
            }
        }

    }


    //수락
    class AdminAcceptItemViewHolder(val binding: AdminPostAcceptedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTOBase) {
            binding.data = data as Admin.Accept
            binding.viewHolder = AdminAdapter()
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): AdminAcceptItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostAcceptedRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_accepted_recycler_item,
                        parent, false
                    )
                return AdminAcceptItemViewHolder(binding)
            }
        }

    }

    //삭제
    class AdminDeleteItemViewHolder(val binding: AdminPostDeleteRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTOBase) {
            binding.data = data
            binding.viewHolder = AdminAdapter()
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

    //대기
    class AdminPendingItemViewHolder(val binding: AdminPostWaitingRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTOBase) {
            binding.data = data
            binding.viewHolder = AdminAdapter()
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


    //거부
    class AdminRejectItemViewHolder(val binding: AdminPostRejectRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTOBase) {
            binding.data = data
            binding.viewHolder = AdminAdapter()
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


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Admin.Accept -> 1
            is Admin.Pending -> 2
            is Admin.Reject -> 3
            is Admin.Delete -> 4
            else -> 0 // loading model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ACCEPTEDType -> AdminAcceptItemViewHolder.from(parent)
            PENDINGType -> AdminPendingItemViewHolder.from(parent)
            REJECTEDType -> AdminRejectItemViewHolder.from(parent)
            DELETEDType -> AdminDeleteItemViewHolder.from(parent)
            else -> AdminAcceptItemViewHolder.from(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            when (holder) {
                is AdminAcceptItemViewHolder -> {
                    holder.bind(item as Admin.Accept)

                    holder.binding.postMore.setOnClickListener {
                        try {
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
                        } catch (e: Exception) {
                            Log.d(AdminAdapter.TAG, "onBindViewHolder: 네비게이션 찾을수 없음")
                        }
                    }
                }
                is AdminPendingItemViewHolder -> {

                    holder.bind(item as Admin.Pending)
                    holder.binding.postMore.setOnClickListener {
                        try {
                            holder.binding.postMore.setOnClickListener {
                                val action =
                                    AdminMainFragmentDirections.actionAdminMainFragmentToPendingDialog(
                                        item.id, holder.bindingAdapterPosition
                                    )
                                it.findNavController().navigateUp()
                                it.findNavController().navigate(action)
                            }
                        } catch (e: Exception) {
                            Log.d("PendingAdapter", "onBindViewHolder: 네비게이션 없음 ")
                        }
                    }
                    holder.binding.postModifyText.setOnClickListener {
                        try {
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
                        } catch (e: Exception) {
                            Log.d("PendingAdapter", "onBindViewHolder: 네비게이션 없음 ")
                        }
                    }

                }
                is AdminDeleteItemViewHolder -> {
                    holder.bind(item as Admin.Delete)
                    holder.binding.postMore.setOnClickListener {
                        try {

                            val action =
                                AdminMainFragmentDirections.actionAdminMainFragmentToDeleteDialog(
                                    item.id, holder.bindingAdapterPosition
                                )
                            it.findNavController().navigateUp()
                            it.findNavController().navigate(action)

                        } catch (e: Exception) {
                            Log.d("DeleteAdapter", "onBindViewHolder: 네비게이션 문제있음")
                        }
                    }
                }
                is AdminRejectItemViewHolder -> {
                    holder.bind(item as Admin.Reject)
                    holder.binding.postMore.setOnClickListener {
                        try {

                            val action =
                                AdminMainFragmentDirections.actionAdminMainFragmentToRejectCancelDialog(
                                    item.id, holder.bindingAdapterPosition
                                )
                            it.findNavController().navigateUp()
                            it.findNavController().navigate(action)
                        } catch (e: Exception) {
                            Log.d("RejectAdapter", "onBindViewHolder: 네비게이션없음")
                        }
                    }
                }

            }
        }

    }

}











