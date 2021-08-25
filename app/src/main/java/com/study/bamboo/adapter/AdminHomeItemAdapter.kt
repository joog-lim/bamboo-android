package com.study.bamboo.adapter

import android.util.Log
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


class AdminHomeItemAdapter(
    private val type: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ACCEPTEDType = 0
        const val PENDINGType = 1
        const val REJECTEDType = 2
        const val DELETEDType = 3
    }

    enum class Status{
        ACCEPTED,PENDING,REJECTED,DELETED
    }

    private var postList = mutableListOf<UserPostDTO>()


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
    class AdminPendingItemViewHolder(val binding: AdminPostWaitingRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserPostDTO) {
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

        return when (type) {

            DELETEDType -> AdminDeleteItemViewHolder.from(parent)
            PENDINGType -> AdminPendingItemViewHolder.from(parent)
            REJECTEDType -> AdminRejectItemViewHolder.from(parent)
            ACCEPTEDType -> AdminAcceptItemViewHolder.from(parent)

            else -> AdminAcceptItemViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AdminRejectItemViewHolder -> {
                holder.bind(postList[position])
                holder.binding.postMore.setOnClickListener {

                    val action =
                        AdminMainFragmentDirections.actionAdminMainFragmentToRejectCancelDialog(
                            postList[0].id
                        )
                    it.findNavController().navigateUp()
                    it.findNavController().navigate(action)
                }

            }
            is AdminDeleteItemViewHolder -> {
                holder.bind(postList[position])
                holder.binding.postMore.setOnClickListener {

                    val action = AdminMainFragmentDirections.actionAdminMainFragmentToDeleteDialog(
                        postList[0].id
                    )
                    it.findNavController().navigateUp()
                    it.findNavController().navigate(action)
                }
            }

            is AdminAcceptItemViewHolder -> {
                holder.bind(postList[position])
                holder.binding.postMore.setOnClickListener {
                    val action = AdminMainFragmentDirections.actionAdminMainFragmentToAcceptDialog(
                        postList[0].id
                    )
                    it.findNavController().navigateUp()
                    it.findNavController().navigate(action)
                }
            }
            is AdminPendingItemViewHolder -> {
                holder.bind(postList[position])
                holder.binding.postMore.setOnClickListener {
                    val action = AdminMainFragmentDirections.actionAdminMainFragmentToPendingDialog(
                        postList[0].id
                    )
                    it.findNavController().navigateUp()
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    fun setItemList(data: List<UserPostDTO>) {
        Log.d("Adapter", "setItemList: $data")
        // 이전꺼, 데이터가 변한값
        val recipesDiffUtil = AdminDiffUtil(postList, data)
        // 데이터의 차이?? 비교 데이터가 무엇이 변햇는지
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        postList = data as MutableList<UserPostDTO>
        //결과가 나오면 전달
        diffUtilResult.dispatchUpdatesTo(this)
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

