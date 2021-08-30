package com.study.bamboo.view.activity.postcreate

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.user.postcreate.PostCreateRequest
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import com.study.bamboo.data.network.models.user.postcreate.Verifier
import com.study.bamboo.view.activity.splash.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostCreateViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    //게시물 업로드 후 리스폰스 받아오기
    val postCreateResponse get() = _postCreateResponse
    private val _postCreateResponse: MutableLiveData<PostCreateResponse> =
        MutableLiveData<PostCreateResponse>()

    //게시물 업로드 후 리스폰스 받아오기
    val postCreateSuccess get() = _postCreateSuccess
    private val _postCreateSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    val choiceTag get() = _choiceTag
    private val _choiceTag: MutableLiveData<String> = MutableLiveData<String>()

    init {
        _postCreateSuccess.value = false
    }

    //스피너로 tag변경할때 tag 값 변경
    fun setChoiceTag(tag: String) {
        _choiceTag.value = tag
    }


    //게시물 올리기
    fun callPostCreateAPI(
        title: String,
        content: String,
        tag: String,
        questionId: String,
        answer: String
    ) = viewModelScope.launch {
        val verifier = Verifier(questionId, answer)
        val postCreateRequest = PostCreateRequest(
            title,
            content,
            tag,
            verifier
        )
        userRepository.transferPostCreate(postCreateRequest).let { response ->
            if (response.isSuccessful)
                _postCreateResponse.value = response.body()
        }
    }

//        retService.transferPostCreate(postCreateRequest).enqueue(object : Callback<PostCreateResponse> {
//            override fun onResponse(
//                call: Call<PostCreateResponse>,
//                response: Response<PostCreateResponse>
//            ) {
//                Log.d("로그","게시물 게시후 ${response.body()?.id}")
//                Log.d("로그","실패  message : ${response.raw()}, errorBody : ${response.isSuccessful}")
//                _postCreateResponse.value = response.body()
//            }
//
//            override fun onFailure(call: Call<PostCreateResponse>, t: Throwable) {
//                Log.d("로그","onFailure : $call, $t")
//            }
//
//        })


}