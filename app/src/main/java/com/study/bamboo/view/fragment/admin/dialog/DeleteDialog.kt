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
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.study.bamboo.adapter.admin.AdminAcceptAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.DELETED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.databinding.DeleteDialogBinding
import com.study.bamboo.utils.Util
import com.study.bamboo.view.activity.signin.SignInActivity
import com.study.bamboo.view.activity.splash.SplashActivity.Companion.deviceSizeX
import com.study.bamboo.view.fragment.admin.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteDialog : DialogFragment() {
    private var _binding: DeleteDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DeleteDialogArgs>()
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
            binding.progressBar.visibility = View.VISIBLE
            val reason: HashMap<String, String> = HashMap()
            reason["reason"] = binding.deleteWhy.text.toString()
            viewModel.deletePost(
                token,
                reason,
                args.auth,
            )

            viewModel.successDeleteData.observe(viewLifecycleOwner) {
                val denied = it?.isEmpty() == true
                binding.progressBar.isVisible = denied
                if (denied) return@observe

                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()

                setNavResult(data = DELETED)
                findNavController().popBackStack()
            }
        }

        binding.rejectBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val reject = HashMap<String, String>()
            reject["status"] = REJECTED
            viewModel.patchPost(
                token,
                args.auth,
                reject,
            )
            viewModel.successPatchData.observe(viewLifecycleOwner) {
                val denied = it?.isEmpty() == true
                binding.progressBar.isVisible = denied
                if (denied) return@observe

                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()

                setNavResult(data = DELETED)
                findNavController().popBackStack()
            }
        }




        return binding.root
    }

    private fun initDialog() {
        //        //디바이스 크기 확인후 커스텀 다이어로그 팝업 크기 조정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = deviceSizeX
        Log.d("로그","acceptDialog : $deviceWidth")
        params?.width = (deviceWidth * 0.9).toInt()


        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun <T> Fragment.setNavResult(key: String = Util.DIALOG_RESULT_KEY, data: T) {
        findNavController().previousBackStackEntry?.also { stack ->
            stack.savedStateHandle.set(key, data)
        }
    }
    companion object {
        const val TAG = "DeleteDialog"
    }
}