package com.study.bamboo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.bamboo.model.dto.GetVerifyDTO
import com.study.bamboo.model.dto.UserGetPostDTO
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.model.retrofit.GetPostAPI
import com.study.bamboo.model.retrofit.GetVerifyAPI
import com.study.bamboo.model.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostCreateViewModel : ViewModel() {

    val getVerifyResponse get() = _getVerifyResponse
    private val _getVerifyResponse: MutableLiveData<GetVerifyDTO> = MutableLiveData<GetVerifyDTO>()


    //verify호출로 id와 질문 가져오기
    fun callGetVerifyAPI(){
        val retService = RetrofitClient().getService().create(GetVerifyAPI::class.java)
        retService.getVerify().enqueue(object : Callback<GetVerifyDTO> {
            override fun onResponse(call: Call<GetVerifyDTO>, response: Response<GetVerifyDTO>) {
                Log.d("로그","Verify 값 : ${response.body()?.id}")
                _getVerifyResponse.value = response.body()
            }

            override fun onFailure(call: Call<GetVerifyDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}