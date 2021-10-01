package com.study.bamboo.view.fragment.user

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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
    private val viewModel by viewModels<DeclarationViewModel>()
    private val args by navArgs<DeclarationFragmentArgs>()
    private var checkPopBackStack = false
    private lateinit var callback: OnBackPressedCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
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
        viewModel.setMessage("none")
        checkPopBackStack = false

        observeViewModel()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkPopBackStack = false

    }

    private fun observeViewModel() {
        viewModel.message.observe(requireActivity(), Observer {
            when (it) {
                "success" -> {
                    Toast.makeText(requireContext(), "신고에 성공했습니다", Toast.LENGTH_SHORT).show()
                    viewModel.setMessage("none")
                    Log.d("로그","신고 성공 후 $checkPopBackStack")
                    if (!checkPopBackStack){
                        checkPopBackStack = true
                        this@DeclarationFragment.findNavController().popBackStack()
                    }
                }
                "fail" ->{ Toast.makeText(requireContext(), "신고에 실패했습니다", Toast.LENGTH_SHORT).show()
                    binding.btn.isEnabled = true
                    binding.btn.setBackgroundColor(Color.parseColor("#E75858"))
                 }
            }
        })
    }

    fun backBtnClick(view: View) {
        this.findNavController().popBackStack()
    }


    fun uploadBtn(view: View) {
        binding.btn.isEnabled = false
        binding.btn.setBackgroundColor(Color.parseColor("#C2C1C1"))
        val body = ReportRequest(binding.contents.text.toString())
        Log.d("로그", "안에 인 : ${args.id}")

        viewModel.report(args.id, body)
    }
}