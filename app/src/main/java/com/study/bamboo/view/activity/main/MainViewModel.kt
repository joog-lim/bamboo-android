package com.study.bamboo.view.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.bamboo.model.dto.UserGetPostDTO
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.model.retrofit.RetrofitClient
import com.study.bamboo.model.retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val getPostResponse : LiveData<List<UserPostDTO>?> get() = _getPostResponse
    private val _getPostResponse: MutableLiveData<List<UserPostDTO>?> = MutableLiveData<List<UserPostDTO>?>()

    //게시물 가져오는 API
    fun callGetPost(count: Int, cursor: String, status: String) {
        val retService = RetrofitClient().getService().create(UserApi::class.java)
        retService.getPost(count, cursor, status).enqueue(object : Callback<UserGetPostDTO> {
            override fun onResponse(
                call: Call<UserGetPostDTO>,
                response: Response<UserGetPostDTO>
            ) {
                _getPostResponse.value = response.body()?.posts
            }

            override fun onFailure(call: Call<UserGetPostDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}