package com.study.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.base.base.adapter.RecyclerViewItemClickListener
import com.study.domain.model.admin.response.PostEntity
import com.study.presentation.R
import com.study.presentation.databinding.AdminPostAcceptedRecyclerItemBinding
import com.study.presentation.databinding.AdminPostDeleteRecyclerItemBinding
import com.study.presentation.databinding.AdminPostRejectRecyclerItemBinding
import com.study.presentation.databinding.AdminPostWaitingRecyclerItemBinding


// TODO


class   AdminAdapter(val onClickListener: RecyclerViewItemClickListener<PostEntity>)

    :
        PagingDataAdapter<PostEntity, RecyclerView.ViewHolder>(diffCallback) {

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

        private val diffCallback = object : DiffUtil.ItemCallback<PostEntity>() {
            override fun areItemsTheSame(
                    oldItem: PostEntity,
                    newItem: PostEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                    oldItem: PostEntity,
                    newItem: PostEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }

    }


    //수락
    inner class AdminAcceptItemViewHolder(val binding: AdminPostAcceptedRecyclerItemBinding) :
            RecyclerView.ViewHolder(binding.root) {


        fun bind(data: PostEntity) {
            binding.data = data
            binding.executePendingBindings()

        }


    }

    //삭제
    inner class AdminDeleteItemViewHolder(val binding: AdminPostDeleteRecyclerItemBinding) :
            RecyclerView.ViewHolder(binding.root) {


        fun bind(data: PostEntity) {
            binding.data = data
            binding.executePendingBindings()
            binding.postMore.setOnClickListener {
                onClickListener.onclick(data)
            }
        }


    }


    //대기
    inner class AdminPendingItemViewHolder(val binding: AdminPostWaitingRecyclerItemBinding) :
            RecyclerView.ViewHolder(binding.root) {


        fun bind(data: PostEntity) {
            binding.data = data
            binding.executePendingBindings()
        }

    }


    //거부
    inner class AdminRejectItemViewHolder(val binding: AdminPostRejectRecyclerItemBinding) :
            RecyclerView.ViewHolder(binding.root) {


        fun bind(data: PostEntity) {
            binding.data = data
            binding.executePendingBindings()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ACCEPTEDType -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostRejectRecyclerItemBinding = DataBindingUtil
                        .inflate(
                                layoutInflater, R.layout.admin_post_reject_recycler_item,
                                parent, false
                        )
                return AdminRejectItemViewHolder(binding)
            }
            PENDINGType -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostWaitingRecyclerItemBinding = DataBindingUtil
                        .inflate(
                                layoutInflater, R.layout.admin_post_waiting_recycler_item,
                                parent, false
                        )
                return AdminPendingItemViewHolder(binding)
            }
            REJECTEDType -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostAcceptedRecyclerItemBinding = DataBindingUtil
                        .inflate(
                                layoutInflater, R.layout.admin_post_accepted_recycler_item,
                                parent, false
                        )
                return AdminAcceptItemViewHolder(binding)
            }
            DELETEDType -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostDeleteRecyclerItemBinding = DataBindingUtil
                        .inflate(
                                layoutInflater, R.layout.admin_post_delete_recycler_item,
                                parent, false
                        )
                return AdminDeleteItemViewHolder(binding)
            }

            else -> throw Exception("잘못된 Adapter 입니다!")

        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            when (holder) {
                is AdminAcceptItemViewHolder -> holder.bind(item)
                is AdminPendingItemViewHolder -> holder.bind(item)
                is AdminDeleteItemViewHolder -> holder.bind(item)
                is AdminRejectItemViewHolder -> holder.bind(item)


            }
        }

    }

}











