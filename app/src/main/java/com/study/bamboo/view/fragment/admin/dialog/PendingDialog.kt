package com.study.bamboo.view.fragment.admin.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.databinding.PendingDialogBinding
import com.study.bamboo.view.activity.signin.SignInActivity
import com.study.bamboo.view.fragment.admin.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingDialog : DialogFragment() {
    private var _binding: PendingDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PendingDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        dialogCorner()
        initDialog()
    }

    private var token = ""

    fun dialogCorner() {
        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private fun initDialog() {
        //        //디바이스 크기 확인후 커스텀 다이어로그 팝업 크기 조정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = SignInActivity.deviceSizeX
        Log.d("로그","acceptDialog : $deviceWidth")
        params?.width = (deviceWidth * 0.9).toInt()


        dialog?.window?.attributes = params as WindowManager.LayoutParams
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

            viewModel.successPatchData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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
            viewModel.successPatchData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
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