package com.study.bamboo.view.user

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
import com.study.bamboo.R
import com.study.bamboo.adapter.TagSpinnerAdapter
import com.study.bamboo.databinding.FragmentPostCreateBinding
import com.study.bamboo.utils.hideKeyBoard
import com.study.bamboo.view.user.viewmodel.AlgorithmViewModel
import com.study.base.base.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostCreateFragment : BaseFragment<FragmentPostCreateBinding>(R.layout.fragment_post_create) {
    private var checkPopBackStack = false
    private lateinit var callback: OnBackPressedCallback
    private val viewModel: AlgorithmViewModel by viewModels()
    private var postTag = "ํ๊ทธ์ ํ"
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
                hideKeyBoard()

            }
            getVerifyResponse.observe(viewLifecycleOwner) {
                getVerify(it?.question)
                questionId = it?.id
            }
            isFailure.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                hideKeyBoard()
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
            binding.question.text = "์๋ฒ ์๋ฌ"

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
            Toast.makeText(requireContext(), "ํ์ํญ๋ชฉ์ ์์ฑํด ์ฃผ์ธ์", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(requireContext(), "์ง๋ฌธ์ ๋ํ ๋ต์ด ์ณ์ง ์์ต๋๋ค", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun textNullCheck(title: String, content: String, answer: String, tag: String) =
        TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || tag == "ํ๊ทธ์ ํ" || TextUtils.isEmpty(
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
                            postTag = "ํ๊ทธ์ ํ"
                        }
                        1 -> {
                            postTag = "์ ๋จธ"

                        }
                        2 -> {
                            postTag = "๊ณต๋ถ"

                        }
                        3 -> {
                            postTag = "์ผ์"

                        }
                        4 -> {
                            postTag = "ํ๊ต"

                        }
                        5 -> {
                            postTag = "์ทจ์"

                        }
                        6 -> {
                            postTag = "๊ด๊ณ"

                        }
                        7 -> {
                            postTag = "๊ธฐํ"

                        }
                        else -> {
                            postTag = "ํ๊ทธ์ ํ"
                        }
                    }
                }
            }

    }
}