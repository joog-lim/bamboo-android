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
import androidx.navigation.fragment.navArgs
import com.study.bamboo.adapter.admin.AdminAcceptAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.adapter.admin.AdminPendingAdapter

import com.study.bamboo.databinding.PendingDialogBinding
import com.study.bamboo.view.fragment.admin.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingDialog : DialogFragment() {
    private var _binding: PendingDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PendingDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()

    private val acceptAdapter: AdminAcceptAdapter by lazy{
        AdminAcceptAdapter()
    }
    private val pendingAdapter: AdminPendingAdapter by lazy{
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
            viewModel.patchPost(
                token,
                args.auth,
                ACCEPTED

            )
          acceptAdapter.notifyDataSetChanged()
            dialog?.hide()
        }

        binding.pendingBtn.setOnClickListener {
            viewModel.patchPost(
                token,
                args.auth,
              REJECTED,

            )
            pendingAdapter.notifyDataSetChanged()
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