package com.study.presentation.view.user

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.study.presentation.R
import com.study.presentation.adapter.TagSpinnerAdapter
import com.study.presentation.databinding.FragmentPostCreateBinding
import com.study.presentation.view.user.viewmodel.AlgorithmViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostCreateFragment : Fragment() {
    private lateinit var binding: FragmentPostCreateBinding
    private var postTag = "태그선택"
    private var checkPopBackStack = false
    private lateinit var callback: OnBackPressedCallback
    private val algorithmViewModel: AlgorithmViewModel by viewModels()
    private var questionId = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("TAG", "handleOnBackPressed: ")
                findNavController().navigate(R.id.action_postCreateFragment_to_userMainFragment)

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_create, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        algorithmViewModel.callGetVerify()
        getVerify()
        observeViewModel()
        setupSpinnerTag()
        setupSpinnerHandler()
    }

    private fun observeViewModel() {
        //게시물을 전송하기 버튼 클릭 후 질문 답 확인
        algorithmViewModel.isSuccess.observe(requireActivity(), Observer {
            if (it) {
                if (!checkPopBackStack) {
                    checkPopBackStack = true
                    lifecycleScope.launchWhenResumed {
                        findNavController().navigate(R.id.action_postCreateFragment_to_userMainFragment)
                    }
                }
            } else {
                binding.uploadBtn.isEnabled = true
                binding.uploadBtn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
        })


    }

    private fun getVerify() {
        algorithmViewModel.getVerifyResponse.observe(requireActivity(), Observer { response ->
            binding.question.text = "Q. ${response.question}"
            questionId = response.id
        })
    }

    fun backBtnClick(view: View) {
        this.findNavController().popBackStack()
    }

    fun postCreateBtnClick(view: View) {
        if (TextUtils.isEmpty(binding.title.text.toString()) || TextUtils.isEmpty(binding.content.text.toString()) || binding.choiceTag.tag == "태그선택" || TextUtils.isEmpty(
                binding.questionAnswer.text.toString()
            )
        ) {
            Toast.makeText(requireContext(), "필수항목을 작성해 주세요", Toast.LENGTH_SHORT).show()
        } else {

            if (questionAnswerTrue(binding.questionAnswer.text.toString())) {

                algorithmViewModel.callPostCreateAPI(
                    binding.title.text.toString(),
                    binding.content.text.toString(),
                    postTag,
                    questionId,
                    binding.questionAnswer.text.toString()
                )
            } else {
                Toast.makeText(requireContext(), "질문에 대한 답이 옳지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //게시글 문제 답 체크
    private fun questionAnswerTrue(answer: String): Boolean {
        return if (answer == "#softmeister01") {
            binding.progressBar.visibility = View.VISIBLE
            binding.uploadBtn.isEnabled = false
            binding.uploadBtn.setBackgroundColor(Color.parseColor("#C2C1C1"))
            true
        } else
            false
    }

    private fun setupSpinnerTag() {
        val tag = resources.getStringArray(R.array.PostCreateTagList)
        binding.choiceTag.adapter =
            TagSpinnerAdapter(requireContext(), tag)
    }

    private fun setupSpinnerHandler() {
        binding.choiceTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        postTag = "태그선택"
                    }
                    1 -> {
                        postTag = "유머"

                    }
                    2 -> {
                        postTag = "공부"

                    }
                    3 -> {
                        postTag = "일상"

                    }
                    4 -> {
                        postTag = "학교"

                    }
                    5 -> {
                        postTag = "취업"

                    }
                    6 -> {
                        postTag = "관계"

                    }
                    7 -> {
                        postTag = "기타"

                    }
                    else -> {
                        postTag = "태그선택"
                    }
                }
                Log.d("로그", "스피너 변동 발생 $postTag")
            }
        }

    }
}