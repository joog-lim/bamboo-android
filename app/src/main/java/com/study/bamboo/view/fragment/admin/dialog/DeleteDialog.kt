package com.study.bamboo.view.fragment.admin.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.databinding.DeleteDialogBinding
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
            viewModel.successPatchData.observe(viewLifecycleOwner){
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            }
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
            viewModel.successDeleteData.observe(viewLifecycleOwner){
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()
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
        const val TAG = "DeleteDialog"
    }
}