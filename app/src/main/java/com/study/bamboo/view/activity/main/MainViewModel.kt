package com.study.bamboo.view.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.user.UserPostDTO
import com.study.bamboo.view.activity.splash.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
)  : ViewModel() {

    val getPostResponse : LiveData<List<UserPostDTO>?> get() = _getPostResponse
    private val _getPostResponse: MutableLiveData<List<UserPostDTO>?> = MutableLiveData<List<UserPostDTO>?>()


    //게시물 가져오는 API
    fun callGetPost(count: Int, cursor: String, status: String) = viewModelScope.launch {
        userRepository.getPost(count, cursor, status).let { response ->
            if (response.isSuccessful){
                _getPostResponse.value = response.body()?.posts
            }
        }
    }

    //게시물 가져오는 API
    //fun callGetPost(count: Int, cursor: String, status: String) {
//        val retService = RetrofitClient().getService().create(GetPostAPI::class.java)
//        retService.getPost(count, cursor, status).enqueue(object : Callback<UserGetPostDTO> {
//            override fun onResponse(
//                call: Call<UserGetPostDTO>,
//                response: Response<UserGetPostDTO>
//            ) {
//                _getPostResponse.value = response.body()?.posts
//            }
//
//            override fun onFailure(call: Call<UserGetPostDTO>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

   // }
}