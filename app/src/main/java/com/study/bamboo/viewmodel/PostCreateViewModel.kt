package com.study.bamboo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.bamboo.model.dto.GetVerifyDTO
import com.study.bamboo.model.dto.postcreate.PostCreateRequest
import com.study.bamboo.model.dto.postcreate.PostCreateResponse
import com.study.bamboo.model.dto.postcreate.Verifier
import com.study.bamboo.model.retrofit.GetVerifyAPI
import com.study.bamboo.model.retrofit.PostCreateAPI
import com.study.bamboo.model.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostCreateViewModel : ViewModel() {

    //verify 값 받아오기
    val getVerifyResponse get() = _getVerifyResponse
    private val _getVerifyResponse: MutableLiveData<GetVerifyDTO> = MutableLiveData<GetVerifyDTO>()


    //게시물 업로드 후 리스폰스 받아오기
    val postCreateResponse get() = _postCreateResponse
    private val _postCreateResponse: MutableLiveData<PostCreateResponse> = MutableLiveData<PostCreateResponse>()

    //게시물 업로드 후 리스폰스 받아오기
    val postCreateSuccess get() = _postCreateSuccess
    private val _postCreateSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>()



    val choiceTag get() = _choiceTag
    private val _choiceTag: MutableLiveData<String> = MutableLiveData<String>()

    init {
        _postCreateSuccess.value = false
    }

    //스피너로 tag변경할때 tag 값 변경
    fun setChoiceTag(tag : String){
        _choiceTag.value = tag
    }


    //verify호출로 id와 질문 가져오기
    fun callGetVerifyAPI(){
        val retService = RetrofitClient().getService().create(GetVerifyAPI::class.java)
        retService.getVerify().enqueue(object : Callback<GetVerifyDTO> {
            override fun onResponse(call: Call<GetVerifyDTO>, response: Response<GetVerifyDTO>) {
                Log.d("로그","Verify 값 : ${response.body()?.id}")
                if(response.body()?.id != null){
                    _getVerifyResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<GetVerifyDTO>, t: Throwable) {

            }

        })
    }


    //게시물 올리기
    fun callPostCreateAPI(title : String, content : String, tag : String, questionId : String, answer : String){

        val retService = RetrofitClient().getService().create(PostCreateAPI::class.java)

        val verifier = Verifier(questionId,answer)
        val postCreateRequest = PostCreateRequest(title,content,tag,verifier)

        retService.transferPostCreate(postCreateRequest).enqueue(object : Callback<PostCreateResponse> {
            override fun onResponse(
                call: Call<PostCreateResponse>,
                response: Response<PostCreateResponse>
            ) {
                Log.d("로그","게시물 게시후 ${response.body()?.id}")
                Log.d("로그","실패  message : ${response.raw()}, errorBody : ${response.isSuccessful}")
                _postCreateResponse.value = response.body()
            }

            override fun onFailure(call: Call<PostCreateResponse>, t: Throwable) {
                Log.d("로그","onFailure : $call, $t")
            }

        })
    }


}