package com.study.presentation.view.user

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.study.base.base.base.BaseFragment
import com.study.presentation.R
import com.study.presentation.adapter.TagSpinnerAdapter
import com.study.presentation.databinding.FragmentPostCreateBinding
import com.study.presentation.view.user.viewmodel.AlgorithmViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostCreateFragment : BaseFragment<FragmentPostCreateBinding>(R.layout.fragment_post_create) {
    private var checkPopBackStack = false
    private lateinit var callback: OnBackPressedCallback
    private val viewModel: AlgorithmViewModel by viewModels()
    private var postTag = "태그선택"
    private var questionId: String? = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                checkPopBackStack

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }


    override fun FragmentPostCreateBinding.onCreateView() {
        binding.fragment = this@PostCreateFragment


    }


    override fun FragmentPostCreateBinding.onViewCreated() {
        viewModel.callGetVerify()
        setupSpinnerTag()
        setupSpinnerHandler()
        with(viewModel) {

            isSuccess.observe(viewLifecycleOwner) {
                checkAnswerQuestion(it)
            }
            getVerifyResponse.observe(viewLifecycleOwner) {
                getVerify(it?.question)
                questionId = it?.id
            }
            isFailure.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            isLoading.observe(viewLifecycleOwner) {
                Log.d("TAG", "onViewCreated: ${it}")
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }

    private fun checkAnswerQuestion(success: Boolean) {
        if (success) {
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
    }


    private fun getVerify(question: String?) {
        if (question?.isBlank() == true) {
            binding.question.text = "서버 에러"

        } else {
            binding.question.text = getString(R.string.question, question)

        }
    }


    fun backBtnClick() {
        findNavController().popBackStack()
    }

    fun postCreateBtnClick() {
        val title = binding.title.text.toString()
        val content = binding.content.text.toString()
        val tag = postTag
        val answer = binding.questionAnswer.text.toString()

        if (textNullCheck(title, content, answer, tag)) {
            Toast.makeText(requireContext(), "필수항목을 작성해 주세요", Toast.LENGTH_SHORT).show()
        } else {
            if (questionAnswerTrue(binding.questionAnswer.text.toString())) {
                viewModel.callPostCreateAPI(
                    title,
                    content,
                    tag,
                    questionId.toString(),
                    answer
                )
            } else {
                Toast.makeText(requireContext(), "질문에 대한 답이 옳지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun textNullCheck(title: String, content: String, answer: String, tag: String) =
        TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || tag == "태그선택" || TextUtils.isEmpty(
            answer
        )


    private fun questionAnswerTrue(answer: String): Boolean {
        return answer == getString(R.string.questionAnswer)
    }


    private fun setupSpinnerTag() {
        val tag = resources.getStringArray(R.array.PostCreateTagList)
        binding.choiceTag.adapter =
            TagSpinnerAdapter(requireContext(), tag)
    }

    private fun setupSpinnerHandler() {
        binding.choiceTag.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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
                }
            }

    }
}