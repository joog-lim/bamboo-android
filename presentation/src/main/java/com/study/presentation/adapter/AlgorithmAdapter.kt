package com.study.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.base.base.adapter.RecyclerViewItemClickListener
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.presentation.R
import com.study.presentation.databinding.*


enum class STATUS {
    USER, ACCEPTED, PENDING, DELETED, REJECTED, REPORTED

}

class AlgorithmAdapter(
    val onClickListener: RecyclerViewItemClickListener<ResultEntity>,
    private val status: STATUS
)

    :
    PagingDataAdapter<ResultEntity, RecyclerView.ViewHolder>(diffCallback) {

    companion object {


        private val diffCallback = object : DiffUtil.ItemCallback<ResultEntity>() {
            override fun areItemsTheSame(
                oldItem: ResultEntity,
                newItem: ResultEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResultEntity,
                newItem: ResultEntity
            ): Boolean {
                return oldItem.idx == newItem.idx
            }
        }

    }

    inner class UserItemViewHolder(val binding: UserPostRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: ResultEntity) {
            binding.data = data
            binding.executePendingBindings()
            binding.declarationBtn.setOnClickListener {
                onClickListener.onStateClick(data, STATUS.USER.toString())
            }
            binding.leafImg.setOnClickListener {
                onClickListener.onLeafClick(data)
            }

        }


    }

    inner class AdminAcceptItemViewHolder(val binding: AdminPostAcceptedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: ResultEntity) {
            binding.result = data
            binding.executePendingBindings()
            binding.postMore.setOnClickListener {
                onClickListener.onStateClick(data, STATUS.ACCEPTED.toString())
            }

        }


    }

    inner class AdminDeleteItemViewHolder(val binding: AdminPostDeleteRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: ResultEntity) {
            binding.result = data

            binding.executePendingBindings()
            binding.postMore.setOnClickListener {
                onClickListener.onStateClick(data, STATUS.DELETED.toString())
            }
        }


    }


    inner class AdminPendingItemViewHolder(val binding: AdminPostWaitingRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: ResultEntity) {
            binding.result = data
            binding.executePendingBindings()
            binding.postMore.setOnClickListener {
                onClickListener.onStateClick(data, STATUS.PENDING.toString())
            }
            binding.postModifyText.setOnClickListener {
                onClickListener.onStateClick(data, STATUS.ACCEPTED.toString())

            }
        }

    }


    //거부
    inner class AdminRejectItemViewHolder(val binding: AdminPostRejectRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: ResultEntity) {
            binding.result = data
            binding.executePendingBindings()
            binding.postMore.setOnClickListener {
                onClickListener.onStateClick(data, STATUS.REJECTED.toString())
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (status) {
            STATUS.USER -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: UserPostRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.user_post_recycler_item,
                        parent, false
                    )
                return UserItemViewHolder(binding)
            }
            STATUS.ACCEPTED -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostAcceptedRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_accepted_recycler_item,
                        parent, false
                    )
                return AdminAcceptItemViewHolder(binding)
            }
            STATUS.PENDING -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostWaitingRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_waiting_recycler_item,
                        parent, false
                    )
                return AdminPendingItemViewHolder(binding)
            }
            STATUS.REJECTED -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostRejectRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_reject_recycler_item,
                        parent, false
                    )
                return AdminRejectItemViewHolder(binding)
            }
            STATUS.DELETED -> {
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
                is UserItemViewHolder -> holder.bind(item)
                is AdminAcceptItemViewHolder -> holder.bind(item)
                is AdminPendingItemViewHolder -> holder.bind(item)
                is AdminDeleteItemViewHolder -> holder.bind(item)
                is AdminRejectItemViewHolder -> holder.bind(item)


            }
        }

    }

}











