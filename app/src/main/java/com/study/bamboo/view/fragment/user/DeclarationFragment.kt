package com.study.bamboo.view.fragment.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.study.bamboo.R
import com.study.bamboo.data.network.models.user.report.ReportRequest
import com.study.bamboo.data.repository.UserRepository
import com.study.bamboo.databinding.FragmentDeclarationBinding
import com.study.bamboo.view.fragment.admin.dialog.AcceptDialogArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DeclarationFragment : Fragment(
) {
    private lateinit var binding: FragmentDeclarationBinding
    private val viewModel by activityViewModels<DeclarationViewModel>()
    private val args by navArgs<DeclarationFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_declaration, container, false)
        binding.fragment = this

        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.message.observe(requireActivity(), Observer {
            when (it) {
                "success" -> {
                    Toast.makeText(requireContext(), "신고에 성공했습니다", Toast.LENGTH_SHORT).show()
                    this@DeclarationFragment.findNavController().popBackStack()
                }
                "fail" -> Toast.makeText(requireContext(), "신고에 실패했습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun uploadBtn(view: View) {
        val body = ReportRequest(binding.contents.text.toString())
        Log.d("로그", "안에 인 : ${args.id}")
        viewModel.report(args.id, body)
    }
}