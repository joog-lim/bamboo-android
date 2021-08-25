package com.study.bamboo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.databinding.*
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.view.fragment.admin.AdminMainFragmentDirections

// TODO: 2021-08-16 어댑터를 나누자 각각의 상황에맞게

enum class Status {
    REJECTED, DELETED, ACCEPTED, PENDING
}

class AdminHomeItemAdapter(
    private val status: Status,

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val postList = mutableListOf<UserPostDTO>()


    //수락
    class AdminAcceptItemViewHolder(val binding: AdminPostAcceptedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTO) {
            binding.data = data
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

    //대기
    class AdminWaitingItemViewHolder(val binding: AdminPostWaitingRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTO) {
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AdminWaitingItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostWaitingRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_waiting_recycler_item,
                        parent, false
                    )
                return AdminWaitingItemViewHolder(binding)
            }
        }
    }

    //거부
    class AdminRejectItemViewHolder(val binding: AdminPostRejectRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTO) {
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

    //삭제
    class AdminDeleteItemViewHolder(val binding: AdminPostDeleteRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTO) {
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (status) {

            Status.DELETED -> AdminDeleteItemViewHolder.from(parent)
            Status.PENDING -> AdminWaitingItemViewHolder.from(parent)
            Status.REJECTED -> AdminRejectItemViewHolder.from(parent)
            Status.ACCEPTED -> AdminAcceptItemViewHolder.from(parent)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AdminRejectItemViewHolder -> {
                holder.bind(postList[position])

            }
            is AdminDeleteItemViewHolder -> holder.bind(postList[position])
            is AdminAcceptItemViewHolder -> holder.bind(postList[position])
            is AdminWaitingItemViewHolder -> {
                holder.bind(postList[position])

                holder.binding.postMore.setOnClickListener {
                    val action = AdminMainFragmentDirections.actionAdminMainFragmentToPendingDialog(
                        postList[0].id
                    )
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    fun setItemList(data: List<UserPostDTO>) {
        // 데이터가 바뀌였으면
        val movieDiffUtil = AdminDiffUtil(postList, data)
        // calculateDiff 아이템 변경을 감지한다.
        val diffUtilResult = movieDiffUtil.let { DiffUtil.calculateDiff(it) }
        // 데이터를 없애고
        postList.clear()
        // 새로운 데이터로 갈아끼운다.
        postList.addAll(data)

        //어뎁터에 감지된 데이터를 전달한다.
        diffUtilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}

//달라진 아이템 갱신
class AdminDiffUtil(
    private val oldList: List<UserPostDTO>,
    private val newList: List<UserPostDTO>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]


    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        return getChangePayload(
            oldItemPosition,
            newItemPosition
        )
    }
}

