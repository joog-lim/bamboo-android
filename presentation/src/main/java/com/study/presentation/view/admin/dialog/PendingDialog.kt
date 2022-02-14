package com.study.presentation.view.admin.dialog

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.study.base.base.base.BaseDialogFragment
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.presentation.R
import com.study.presentation.adapter.STATUS
import com.study.presentation.databinding.PendingDialogBinding
import com.study.presentation.utils.setNavResult
import com.study.presentation.view.admin.AdminViewModel
import com.study.presentation.view.admin.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PendingDialog : BaseDialogFragment<PendingDialogBinding>(R.layout.pending_dialog) {

    private val args by navArgs<PendingDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()


    override fun PendingDialogBinding.onViewCreated() {
        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner) {
                if (it.contains(STATUS.ACCEPTED.toString())) {
                    setNavResult(data = STATUS.ACCEPTED)
                    findNavController().popBackStack()
                } else {
                    setNavResult(data = STATUS.REJECTED)
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

    fun acceptPost() {
        Log.d("TAG", "acceptPost: ")
        lifecycleScope.launch {
            viewModel.patchPost(
                authViewModel.getToken(),
                args.result.idx,
                SetStatusEntity(STATUS.ACCEPTED.toString(),binding.reasonText.text.toString())
            )
        }
    }


    fun rejectPost() {

        lifecycleScope.launch {
            viewModel.patchPost(
                authViewModel.getToken(),
                args.result.idx,
                SetStatusEntity(STATUS.REJECTED.toString(),binding.reasonText.text.toString())
            )
        }

    }

    override fun PendingDialogBinding.onCreateView() {
        binding.dialog=this@PendingDialog
    }
}