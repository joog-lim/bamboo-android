package com.study.bamboo.view.activity.postcreate

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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.study.bamboo.databinding.FragmentPostCreateBinding
import dagger.hilt.android.AndroidEntryPoint
import com.study.bamboo.R


@AndroidEntryPoint
class PostCreateFragment : Fragment() {
    private val postCreateViewModel: PostCreateViewModel by activityViewModels()
    private lateinit var binding: FragmentPostCreateBinding
    private var postTag = "태그선택"
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
        initApiCall()
        initResult()
        observeViewModel()
        setupSpinnerTag()
        setupSpinnerHandler()
        return binding.root
    }

    private fun initApiCall() {
        postCreateViewModel.callGetVerify()
    }

    private fun initResult() {
        postCreateViewModel.setPostCreateResponse(null)
        postCreateViewModel.setPostCreateSuccess(null)
        postCreateViewModel.setGetVerifyResponse(null)
    }

    private fun observeViewModel() {
        //게시물을 전송하기 버튼 클릭 후 질문 답 확인
        postCreateViewModel.postCreateResponse.observe(requireActivity(), Observer {
            binding.progressBar.visibility = View.GONE
            if (it != null) {
                if (!checkPopBackStack){
                    checkPopBackStack= true
                    lifecycleScope.launchWhenResumed {
                        findNavController().navigate(R.id.action_postCreateFragment_to_userMainFragment)
                    }
                }
            }else{
                binding.uploadBtn.isEnabled = true
                binding.uploadBtn.resources.getColor(R.color.main_color)
            }
        })

        postCreateViewModel.getVerifyResponse.observe(requireActivity(), Observer {
            binding.question.text = "Q. ${it?.body()?.question}"
        })
    }

    fun backBtnClick(view: View) {
        this.findNavController().popBackStack()
    }

    fun postCreateBtnClick(view: View) {
        if (TextUtils.isEmpty(binding.title.text.toString()) || TextUtils.isEmpty(binding.content.text.toString()) || postCreateViewModel.choiceTag.value == "태그선택" || TextUtils.isEmpty(
                binding.questionAnswer.text.toString()
            )
        ) {
            Toast.makeText(requireContext(), "필수항목을 작성해 주세요", Toast.LENGTH_SHORT).show()
        } else {

            if (questionAnswerTrue(binding.questionAnswer.text.toString())) {
                binding.progressBar.visibility = View.VISIBLE
                binding.uploadBtn.isEnabled = false
                binding.uploadBtn.setBackgroundColor(Color.parseColor("#C2C1C1"))

                postCreateViewModel.getVerifyResponse.let {
                    postCreateViewModel.callPostCreateAPI(
                        binding.title.text.toString(),
                        binding.content.text.toString(),
                        postTag,
                        it.value?.body()!!.id,
                        binding.questionAnswer.text.toString()
                    )
                }
            } else {
                Toast.makeText(requireContext(), "질문에 대한 답이 옳지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //게시글 문제 답 체크
    private fun questionAnswerTrue(answer: String): Boolean {
        return answer == "#softmeister01"
    }

    private fun setupSpinnerTag() {
        val tag = resources.getStringArray(R.array.PostCreateTagList)
        binding.choiceTag.adapter=  TagSpinnerAdapter(requireContext(),tag)
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
                Log.d("로그","스피너 변동 발생 $postTag")
                postCreateViewModel.setChoiceTag(postTag)
            }
        }

    }
}