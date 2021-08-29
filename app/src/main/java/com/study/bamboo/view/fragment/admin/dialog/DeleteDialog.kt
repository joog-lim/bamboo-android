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
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.adapter.admin.AdminDeleteAdapter
import com.study.bamboo.databinding.DeleteDialogBinding
import com.study.bamboo.view.fragment.admin.AdminViewModel
import com.study.bamboo.view.fragment.admin.paging.viewModel.PagingPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeleteDialog : DialogFragment() {
    private var _binding: DeleteDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DeleteDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()
    private  val pagingViewModel: PagingPostViewModel by viewModels()
    private val deleteAdapter: AdminDeleteAdapter by lazy {
        AdminDeleteAdapter()
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
        _binding = DeleteDialogBinding.inflate(inflater, container, false)

        Log.d(TAG, "id: ${args.auth}")
        viewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
            token = it.token

            Log.d(TAG, "observeUiPreferences: ${it.token}")

        })

        binding.deleteBtn.setOnClickListener {


            viewModel.deletePost(
                token,
                "왤끼요",
                args.auth,
                )
            deleteAdapter.notifyDataSetChanged()
            dialog?.hide()
        }

        binding.rejectBtn.setOnClickListener {
            val reject = HashMap<String, String>()
            reject["status"] = REJECTED
            viewModel.patchPost(
                token,
                args.auth,
                reject,
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

    private fun updateData(){
        lifecycleScope.launch {
            pagingViewModel.deleteData .collectLatest{
                deleteAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "DeleteDialog"
    }
}