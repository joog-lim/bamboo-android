package com.study.bamboo.view.activity.postcreate

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivityPostCreateBinding
import com.study.bamboo.utils.ViewModel.postCreateViewModel
import com.study.bamboo.utils.ViewModel.splashViewModel
import com.study.bamboo.view.base.BaseActivity

class PostCreateActivity : BaseActivity() {

    private val binding by binding<ActivityPostCreateBinding>(R.layout.activity_post_create)
    private var tag = "태그선택"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_create)
        binding.activity = this
        supportActionBar!!.hide()
        binding.question.text = "Q. ${splashViewModel.getVerifyResponse.value?.question}"
        setupSpinnerTag()
        setupSpinnerHandler()
        observeViewModel()
    }

    private fun observeViewModel() {
        //게시물을 전송하기 버튼 클릭 후 질문 답 확인
        postCreateViewModel.postCreateResponse.observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            if (it != null) {
                postCreateViewModel.postCreateSuccess.value = true
            }
        })


        //게시물을 성공적으로 전송했는지 확인
        postCreateViewModel.postCreateSuccess.observe(this, Observer {
            if (it == true) {
                finish()
            }
        })
    }

    fun backBtnClick(view: View) {
        finish()
    }

    fun postCreateBtnClick(view: View) {
        if (TextUtils.isEmpty(binding.title.text.toString()) || TextUtils.isEmpty(binding.content.text.toString()) || postCreateViewModel.choiceTag.value == "태그선택" || TextUtils.isEmpty(
                binding.questionAnswer.text.toString()
            )
        ) {
            Toast.makeText(this, "필수항목을 작성해 주세요", Toast.LENGTH_SHORT).show()
        } else {

            if (questionAnswerTrue(binding.questionAnswer.text.toString())) {
                binding.progressBar.visibility = View.VISIBLE
                splashViewModel.getVerifyResponse.value?.let {
                    postCreateViewModel.callPostCreateAPI(
                        binding.title.text.toString(),
                        binding.content.text.toString(),
                        tag,
                        it.id,
                        binding.questionAnswer.text.toString()
                    )
                }

            } else {
                Toast.makeText(this, "질문에 대한 답이 옳지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //게시글 문제 답 체크
    private fun questionAnswerTrue(answer: String): Boolean {
        return answer == "#softmeister01"
    }

    private fun setupSpinnerTag() {
        binding.choiceTag.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.PostCreateTagList,
            R.layout.post_create_tag_spinner_item
        )
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
                        tag = "태그선택"
                    }
                    1 -> {
                        tag = "유머"

                    }
                    2 -> {
                        tag = "공부"

                    }
                    3 -> {
                        tag = "일상"

                    }
                    4 -> {
                        tag = "학교"

                    }
                    5 -> {
                        tag = "취업"

                    }
                    6 -> {
                        tag = "관계"

                    }
                    7 -> {
                        tag = "기타"

                    }
                    else -> {
                        tag = "태그선택"
                    }
                }
                postCreateViewModel.setChoiceTag(tag)
            }
        }

    }

}