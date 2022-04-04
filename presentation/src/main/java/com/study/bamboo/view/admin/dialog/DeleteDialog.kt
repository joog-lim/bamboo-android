package com.study.bamboo.view.admin.dialog

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.study.bamboo.R
import com.study.bamboo.adapter.STATUS
import com.study.bamboo.databinding.DeleteDialogBinding
import com.study.bamboo.utils.setNavResult
import com.study.bamboo.view.admin.AdminViewModel
import com.study.bamboo.view.admin.AuthViewModel
import com.study.base.base.base.BaseDialogFragment
import com.study.domain.model.admin.request.SetStatusEntity

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DeleteDialog : BaseDialogFragment<DeleteDialogBinding>(R.layout.delete_dialog) {

    private val args by navArgs<DeleteDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()


    fun buttonClickListener(view: View) {
        when (view.id) {
            R.id.delete_btn -> {
                deletePost()
            }
            R.id.reject_btn -> {
                rejectPost()
            }
        }
    }

    private fun deletePost() {
        lifecycleScope.launch {
            viewModel.deletePost(
                authViewModel.getToken(),
                args.result.idx,
            )
        }


    }

    override fun DeleteDialogBinding.onViewCreated() {
        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner) {
                if (it.contains(STATUS.REJECTED.toString())) {
                    setNavResult(data = STATUS.REJECTED)
                    findNavController().popBackStack()
                } else {
                    setNavResult(data = STATUS.DELETED)
                    findNavController().popBackStack()
                }

            }
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE

                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
            isFailure.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun rejectPost() {
        lifecycleScope.launch {
            viewModel.patchPost(
                authViewModel.getToken(),
                args.result.idx,
                SetStatusEntity(STATUS.REJECTED.toString(),binding.reasonText.text.toString()),
            )
        }

    }
}

