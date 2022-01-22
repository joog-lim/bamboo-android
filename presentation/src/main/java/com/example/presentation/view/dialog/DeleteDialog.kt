package com.example.presentation.view.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.presentation.adapter.AdminAdapter.Companion.DELETED
import com.example.presentation.adapter.AdminAdapter.Companion.REJECTED
import com.example.presentation.view.admin.AdminViewModel
import com.example.domain.model.admin.request.SetStatusEntity
import com.example.presentation.databinding.DeleteDialogBinding
import com.example.presentation.utils.setNavResult
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
//        authViewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
//            token = it.token
//
//
//        })

        binding.deleteBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            deletePost()
        }

        binding.rejectBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            rejectPost()

        }


        return binding.root
    }

    private fun deletePost() {
        view?.let { it1 -> hideKeyboardFrom(requireContext(), it1) }
        viewModel.deletePost(
            token,
            args.auth,
        )

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            val denied = it?.isEmpty() == true
            binding.progressBar.isVisible = denied
            if (denied) return@observe

            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()

            setNavResult(data = DELETED)
            findNavController().popBackStack()
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }

    private fun rejectPost() {

        viewModel.patchPost(
            token,
            args.auth,
            SetStatusEntity(REJECTED),
        )
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            val denied = it?.isEmpty() == true
            binding.progressBar.isVisible = denied
            if (denied) return@observe

            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()

            setNavResult(data = DELETED)
            findNavController().popBackStack()
        }
    }

    private fun initDialog() {
        //        //디바이스 크기 확인후 커스텀 다이어로그 팝업 크기 조정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
/*        val deviceWidth = deviceSizeX
        Log.d("로그", "acceptDialog : $deviceWidth")
        params?.width = (deviceWidth * 0.9).toInt()*/


        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val TAG = "DeleteDialog"
    }
}