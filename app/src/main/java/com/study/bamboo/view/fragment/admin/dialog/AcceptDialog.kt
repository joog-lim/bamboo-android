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
import com.study.bamboo.databinding.AcceptDialogBinding
import com.study.bamboo.view.fragment.admin.AdminViewModel
import com.study.bamboo.view.fragment.admin.paging.viewModel.PagingPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AcceptDialog : DialogFragment() {
    private var _binding: AcceptDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AcceptDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()
    private val pagingViewModel: PagingPostViewModel by viewModels()
    private val acceptAdapter: AdminAcceptAdapter by lazy {
        AdminAcceptAdapter()
    }
    private var job: Job? = null

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

            updatePost()
            dialog?.hide()

        }






        return binding.root
    }


    private  fun updatePost() {
        job?.cancel()
        job = lifecycleScope.launch {

            viewModel.acceptPatchPost(
                token,
                args.auth,
                bodySend()
            )
        }




    }



    private fun bodySend(): HashMap<String, String> {
        val accepted: HashMap<String, String> = HashMap()
        accepted["title"] = binding.updateTitle.text.toString()
        accepted["content"] = binding.updateContent.text.toString()
        accepted["tag"] = binding.updateTag.text.toString()

        return accepted
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "LoginDialog"
    }
}