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
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.adapter.admin.AdminPendingAdapter
import com.study.bamboo.databinding.PendingDialogBinding
import com.study.bamboo.view.fragment.admin.AdminViewModel
import com.study.bamboo.data.paging.viewModel.PagingPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PendingDialog : DialogFragment() {
    private var _binding: PendingDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PendingDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()
    private  val pagingViewModel: PagingPostViewModel by viewModels()

    private val pendingAdapter: AdminPendingAdapter by lazy {
        AdminPendingAdapter()
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
        _binding = PendingDialogBinding.inflate(inflater, container, false)


        viewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
            token = it.token
            Log.d(TAG, "observeUiPreferences: ${it.token}")

        })

        binding.acceptBtn.setOnClickListener {
            Log.d(TAG, "PendingDialog token: $token, id : ${args.auth}, status: $ACCEPTED")
            val accepted = HashMap<String, String>()
            accepted["status"] = ACCEPTED

            viewModel.patchPost(
                token = token,
                args.auth,
                accepted
            )


            viewModel.successData.observe(viewLifecycleOwner){
                if(it){
                    updateData()
                }
            }
            dialog?.hide()
        }


        binding.pendingBtn.setOnClickListener {
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
            pagingViewModel.pendingData .collectLatest{
                pendingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "LoginDialog"
    }
}