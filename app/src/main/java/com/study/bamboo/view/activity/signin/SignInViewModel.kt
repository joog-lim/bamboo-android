package com.study.bamboo.view.activity.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.bamboo.model.dto.UserGetPostDTO
import com.study.bamboo.model.dto.AdminSignInDTO
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.model.retrofit.AdminLoginAPI
import com.study.bamboo.model.retrofit.GetPostAPI
import com.study.bamboo.model.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {
    val display_size_x get() = _display_size_x
    private val _display_size_x: MutableLiveData<Int> = MutableLiveData<Int>()

    private val _dataSuccess = MutableLiveData<Boolean>()
    val dataSuccess: LiveData<Boolean> get() = _dataSuccess

    val adminLoginResponse get() = _adminLoginResponse
    private val _adminLoginResponse: MutableLiveData<String> = MutableLiveData<String>()



    val getPostResponse get() = _getPostResponse
    private val _getPostResponse: MutableLiveData<List<UserPostDTO>?> =
        MutableLiveData<List<UserPostDTO>?>()

    init {
        display_size_x.value = 0
        _dataSuccess.value = false
    }



    //관리자 로그인 API
    fun callAdminLoginAPI(password: String) {


        val retService = RetrofitClient().getService().create(AdminLoginAPI::class.java)


        val passwordRequest = HashMap<String, String>()
        passwordRequest.put("password", password)

        retService.transferAdminLogin(passwordRequest)?.enqueue(object : Callback<AdminSignInDTO> {
            override fun onResponse(
                call: Call<AdminSignInDTO>,
                response: Response<AdminSignInDTO>
            ) {
                if (response.isSuccessful) {
                    _adminLoginResponse.value = response.body()?.token.toString()
                    _dataSuccess.value = true
                }

            }

            override fun onFailure(call: Call<AdminSignInDTO>, t: Throwable) {

            }

        })

    }

    //게시물 가져오는 API
    fun callGetPost(count: Int, cursor: String, status: String) {
        val retService = RetrofitClient().getService().create(GetPostAPI::class.java)
        retService.getPost(count, cursor, status).enqueue(object : Callback<UserGetPostDTO> {
            override fun onResponse(
                call: Call<UserGetPostDTO>,
                response: Response<UserGetPostDTO>
            ) {
                Log.d("로그", "리스폰스 : ${response.body()?.posts?.get(0)?.title}")
                _getPostResponse.value = response.body()?.posts
                Log.d(
                    "로그",
                    "리스폰스 : ${_getPostResponse.value?.get(0)?.title}, 사이즈 : ${_getPostResponse.value?.size}"
                )
            }

            override fun onFailure(call: Call<UserGetPostDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

}