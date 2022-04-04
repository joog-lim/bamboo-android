package com.study.bamboo.view.admin.dialog

import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.study.bamboo.R
import com.study.bamboo.adapter.STATUS
import com.study.bamboo.databinding.AcceptDialogBinding
import com.study.bamboo.utils.setNavResult
import com.study.bamboo.view.admin.AdminViewModel
import com.study.bamboo.view.admin.AuthViewModel
import com.study.base.base.base.BaseDialogFragment
import com.study.domain.model.admin.request.AlgorithmModifyEntity

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AcceptDialog : BaseDialogFragment<AcceptDialogBinding>(R.layout.accept_dialog) {


    private val args by navArgs<AcceptDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun AcceptDialogBinding.onCreateView() {
        binding.apply {
            result = args.result
            dialog = this@AcceptDialog
        }
    }

    override fun AcceptDialogBinding.onViewCreated() {

        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner) {

                setNavResult(data = STATUS.ACCEPTED)
                findNavController().popBackStack()

            }
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    view?.let { it1 -> hideKeyboardFrom(requireContext(), it1) }
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


    private fun updatePost() {
        lifecycleScope.launch {

            viewModel.acceptPatchPost(
                authViewModel.getToken(),
                args.result.idx,
                AlgorithmModifyEntity(
                    binding.updateTitle.text.toString(),
                    binding.updateContent.text.toString(),
                )
            )

        }

    }

    fun acceptButtonClickListener() {
        updatePost()
    }
}



