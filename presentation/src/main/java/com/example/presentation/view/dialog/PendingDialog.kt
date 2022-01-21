package com.example.presentation.view.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.admin.request.SetStatusEntity
import com.example.presentation.adapter.AdminAdapter.Companion.ACCEPTED
import com.example.presentation.adapter.AdminAdapter.Companion.PENDING
import com.example.presentation.adapter.AdminAdapter.Companion.REJECTED
import com.example.presentation.databinding.PendingDialogBinding
import com.example.presentation.utils.setNavResult
import com.example.presentation.view.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingDialog() : DialogFragment() {
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
//        val deviceWidth = deviceSizeX
//        Log.d("로그", "acceptDialog : $deviceWidth")
//        params?.width = (deviceWidth * 0.9).toInt()


        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PendingDialogBinding.inflate(inflater, container, false)


//        authViewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
//            token = it.token
//        })

        binding.acceptBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            acceptPost()
        }

        binding.pendingBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            deletePost()
        }



        return binding.root
    }

    private fun acceptPost() {
        view?.let { it1 -> hideKeyboardFrom(requireContext(), it1) }

        viewModel.patchPost(
            token = token,
            args.auth,
            SetStatusEntity(ACCEPTED)
        )
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            val denied = it?.isEmpty() == true
            binding.progressBar.isVisible = denied
            if (denied) return@observe

            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()

            setNavResult(data = PENDING)
            findNavController().popBackStack()
        }
    }

    private fun deletePost() {
        view?.let { it1 -> hideKeyboardFrom(requireContext(), it1) }

        viewModel.patchPost(
            token,
            args.auth,
            SetStatusEntity(REJECTED)
        )
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            val denied = it?.isEmpty() == true
            binding.progressBar.isVisible = denied
            if (denied) return@observe

            val toast = Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 400, 550)
            toast.show()

            setNavResult(data = PENDING)
            findNavController().popBackStack()
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "LoginDialog"


    }
}