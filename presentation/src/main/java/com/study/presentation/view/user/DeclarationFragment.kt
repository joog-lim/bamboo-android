package com.study.presentation.view.user

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.study.base.base.base.BaseDialogFragment
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.presentation.R
import com.study.presentation.adapter.STATUS
import com.study.presentation.databinding.FragmentDeclarationBinding
import com.study.presentation.view.admin.AdminViewModel
import com.study.presentation.view.admin.AuthViewModel
import com.study.presentation.view.user.viewmodel.AlgorithmViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DeclarationFragment :
    BaseDialogFragment<FragmentDeclarationBinding>(R.layout.fragment_declaration) {
    private val adminViewModel by viewModels<AdminViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    private val args by navArgs<DeclarationFragmentArgs>()

    override fun FragmentDeclarationBinding.onCreateView() {
        fragment = this@DeclarationFragment
    }

    override fun FragmentDeclarationBinding.onViewCreated() {
        with(adminViewModel) {
            isSuccess.observe(requireActivity(), Observer {
                Toast.makeText(requireContext(), "신고에 성공했습니다", Toast.LENGTH_SHORT).show()
                dismiss()


            })
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.btn.apply {
                        isEnabled = false
                        setBackgroundResource(R.color.no_click_color)
                    }
                    binding.progressBar.visibility = View.VISIBLE

                } else {
                    binding.btn.apply {
                        isEnabled = true
                        setBackgroundResource((R.color.report_color))
                    }
                    binding.progressBar.visibility = View.GONE

                }
            }
            isFailure.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideKeyboardFrom(requireContext(), binding.root)
    }

    fun uploadBtn() {
        lifecycleScope.launch {
            adminViewModel.patchPost(
                authViewModel.getToken(),
                args.id,
                SetStatusEntity(STATUS.REPORTED.toString(), binding.contents.text.toString())
            )
        }
    }
}