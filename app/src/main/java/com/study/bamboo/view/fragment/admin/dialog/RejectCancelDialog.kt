package com.study.bamboo.view.fragment.admin.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.study.bamboo.adapter.admin.AdminAcceptAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTED
import com.study.bamboo.adapter.admin.AdminRejectAdapter


import com.study.bamboo.databinding.RejectCancelDialogBinding
import com.study.bamboo.view.fragment.admin.AdminViewModel
import com.study.bamboo.data.paging.viewModel.PagingPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RejectCancelDialog : DialogFragment() {
    private var _binding: RejectCancelDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<RejectCancelDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()
    private  val pagingViewModel: PagingPostViewModel by viewModels()
    private val rejectAdapter: AdminRejectAdapter by lazy{
        AdminRejectAdapter()
    }
    override fun onResume() {
        super.onResume()
        dialogCorner()

    }

    private var token = ""

    fun dialogCorner() {
        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RejectCancelDialogBinding.inflate(inflater, container, false)


        viewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
            token = it.token
            Log.d(TAG, "onCreateView: ${args.auth}")
            Log.d(TAG, "observeUiPreferences: ${it.token}")

        })


        binding.rejectCancelBtn.setOnClickListener {

            val accept = HashMap<String, String>()
            accept["status"] = AdminAcceptAdapter.ACCEPTED
            viewModel.patchPost(
                token,
                args.auth,
                accept,

            )
            viewModel.successData.observe(viewLifecycleOwner){
                if(it){
                    updateData()
                }
            }
            dialog?.hide()
        }





        return binding.root
    }
    private fun updateData() {
        lifecycleScope.launch {
            pagingViewModel.rejectData.collectLatest {
                rejectAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "RejectCancelDialog"
    }
}