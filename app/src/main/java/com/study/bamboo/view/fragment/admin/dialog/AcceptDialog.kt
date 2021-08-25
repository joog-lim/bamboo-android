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
import com.study.bamboo.adapter.AdminHomeItemAdapter
import com.study.bamboo.adapter.Status
import com.study.bamboo.databinding.AcceptDialogBinding
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.view.fragment.admin.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptDialog : DialogFragment() {
    private var _binding: AcceptDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AcceptDialogArgs>()
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
        _binding = AcceptDialogBinding.inflate(inflater, container, false)


        viewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
            token = it.token
            Log.d(TAG, "observeUiPreferences: ${it.token}")

        })


        binding.acceptBtn.setOnClickListener {
            viewModel.patchPost(
                token,
                args.auth,
                Status.REJECTED,
                "",
                "",
                ""
            )
            observePatchPost(AdminHomeItemAdapter(Status.ACCEPTED))
            dialog?.hide()
        }




        return binding.root
    }

    private fun observePatchPost(adapter: AdminHomeItemAdapter) {
        viewModel.patchPostDto.observe(viewLifecycleOwner, {
            Log.d(TAG, "observeGetPost: $it")
            val userPostDto = UserPostDTO(
                it.content,
                it.createdAt,
                it.id,
                it.number,
                it.status,
                it.tag,
                it.title
            )
            Log.d(TAG, "observePatchPost: $it")
            adapter.setItemList(listOf(userPostDto))
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "LoginDialog"
    }
}