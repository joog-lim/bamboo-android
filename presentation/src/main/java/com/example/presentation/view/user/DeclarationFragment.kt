package com.example.presentation.view.user

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.user.request.Report
import com.example.presentation.R
import com.example.presentation.databinding.FragmentDeclarationBinding
import com.example.presentation.view.user.viewmodel.AlgorithmViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeclarationFragment : Fragment(
) {
    private lateinit var binding: FragmentDeclarationBinding
    private val viewModel by viewModels<AlgorithmViewModel>()
    private val args by navArgs<DeclarationFragmentArgs>()
    private var checkPopBackStack = false
    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("TAG", "handleOnBackPressed: ")
                findNavController().navigate(R.id.action_declarationFragment_to_userMainFragment)

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_declaration, container, false)
        binding.fragment = this
        checkPopBackStack = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

    }

    override fun onResume() {
        super.onResume()
        checkPopBackStack = false

    }

    private fun observeViewModel() {
        viewModel.isSuccess.observe(requireActivity(), Observer {
            if (it) {
                Toast.makeText(requireContext(), "신고에 성공했습니다", Toast.LENGTH_SHORT).show()
                Log.d("로그", "신고 성공 후 $checkPopBackStack")
                if (!checkPopBackStack) {
                    checkPopBackStack = true
                    this@DeclarationFragment.findNavController().popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "신고에 실패했습니다", Toast.LENGTH_SHORT).show()
                binding.btn.isEnabled = true
                binding.btn.setBackgroundColor(Color.parseColor("#E75858"))
            }
        })
    }

    fun backBtnClick(view: View) {
        this.findNavController().popBackStack()
    }


    fun uploadBtn(view: View) {
        binding.btn.isEnabled = false
        binding.btn.setBackgroundColor(Color.parseColor("#C2C1C1"))
        val reportText = binding.contents.text.toString()
        Log.d("로그", "안에 인 : ${args.id}")

        viewModel.report(args.id, Report(reportText))
    }
}