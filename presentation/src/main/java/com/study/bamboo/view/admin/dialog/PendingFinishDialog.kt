package com.study.bamboo.view.admin.dialog

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.study.bamboo.R
import com.study.bamboo.adapter.STATUS
import com.study.bamboo.databinding.FragmentRejectFinishDialogBinding
import com.study.bamboo.utils.setNavResult
import com.study.bamboo.view.admin.AdminViewModel
import com.study.bamboo.view.admin.AuthViewModel
import com.study.bamboo.view.user.viewmodel.AlgorithmViewModel
import com.study.base.base.base.BaseDialogFragment
import com.study.domain.model.admin.request.SetStatusEntity

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PendingFinishDialog :
    BaseDialogFragment<FragmentRejectFinishDialogBinding>(R.layout.fragment_reject_finish_dialog) {

    private val viewModel: AdminViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val algorithmViewModel: AlgorithmViewModel by activityViewModels()

    override fun FragmentRejectFinishDialogBinding.onCreateView() {
        binding.dialog = this@PendingFinishDialog
        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner) {
                if (it.contains(STATUS.REJECTED.toString())) {
                    setNavResult(data = STATUS.REJECTED)
                    findNavController().navigateUp()
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


    fun rejectPost() {

        lifecycleScope.launch {

            viewModel.patchPost(
                authViewModel.getToken(),
                algorithmViewModel.idx.value?.toInt() ?: 0,
                SetStatusEntity(STATUS.REJECTED.toString(), binding.reasonText.text.toString())
            )
        }
    }


}