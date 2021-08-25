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
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.study.bamboo.R
import com.study.bamboo.adapter.Status
import com.study.bamboo.databinding.PendingDialogBinding
import com.study.bamboo.view.activity.signin.SignInViewModel
import com.study.bamboo.view.fragment.admin.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingDialog : DialogFragment() {
    private var _binding: PendingDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PendingDialogArgs>()
    private val tokenViewModel: SignInViewModel by navGraphViewModels(R.id.action_adminMainFragment_to_pendingDialog)
    private val viewModel: AdminViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        dialogCorner()

    }


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

        Log.d(TAG, "token:${tokenViewModel.adminLoginResponse}: ")
        binding.acceptBtn.setOnClickListener {
            Log.d(TAG, "acceptBtn: ${args.auth} token:${tokenViewModel.adminLoginResponse}")
            viewModel.patchPost(tokenViewModel.adminLoginResponse.toString(),args.auth, Status.ACCEPTED,"","","")
            dialog?.hide()
        }

        binding.pendingBtn.setOnClickListener {
            Log.d(TAG, "pendingBtn: ${args.auth} token:${tokenViewModel.adminLoginResponse}")
            viewModel.patchPost(tokenViewModel.adminLoginResponse.toString(),args.auth, Status.REJECTED,"","","")
            dialog?.hide()
        }




        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "LoginDialog"
    }
}