package com.study.bamboo.view.activity.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.user.UserPostDTO
import com.study.bamboo.data.repository.remote.AdminRepository
import com.study.bamboo.view.activity.splash.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository
) : ViewModel() {


     private val _adminLoginResponse: MutableLiveData<String> = MutableLiveData<String>()
    val adminLoginResponse : MutableLiveData<String> get() = _adminLoginResponse

    val getPostResponse get() = _getPostResponse
    private val _getPostResponse: MutableLiveData<List<UserPostDTO>?> =
        MutableLiveData<List<UserPostDTO>?>()

    val success= MutableLiveData<Boolean>()
    init {
        success.value=false
    }



    //관리자 로그인 API
    fun callAdminLoginAPI(password : String) {
        val passwordRequest = HashMap<String, String>()
        passwordRequest.put("password", password)
        viewModelScope.launch {
            adminRepository.transferAdminLogin(passwordRequest).let { response ->
                if (response != null) {
                    if (response.isSuccessful) {
                        _adminLoginResponse.value = response.body()?.token.toString()
                        success.value = true
                    }
                }
            }
        }
    }



//        val retService = RetrofitClient().getService().create(AdminLoginAPI::class.java)


//        val passwordRequest = HashMap<String, String>()
//        passwordRequest.put("password", password)
//
//        retService.transferAdminLogin(passwordRequest)?.enqueue(object : Callback<AdminSignInDTO> {
//            override fun onResponse(
//                call: Call<AdminSignInDTO>,
//                response: Response<AdminSignInDTO>
//            ) {
//                if (response.isSuccessful) {
//                    _adminLoginResponse.value = response.body()?.token.toString()
//                    success.value=true
//                }
//
//            }
//
//            override fun onFailure(call: Call<AdminSignInDTO>, t: Throwable) {
//
//            }
//
//        })



    //게시물 가져오는 API
    fun callGetPost(count: Int, cursor: String, status: String) = viewModelScope.launch {
        userRepository.getPost(count, cursor, status).let { response ->
            if (response.isSuccessful){
                _getPostResponse.value = response.body()?.posts
            }
        }
    }

//    val retService = RetrofitClient().getService().create(GetPostAPI::class.java)
//    retService.getPost(count, cursor, status).enqueue(object : Callback<UserGetPostDTO> {
//        override fun onResponse(
//            call: Call<UserGetPostDTO>,
//            response: Response<UserGetPostDTO>
//        ) {
//            //Log.d("로그", "리스폰스 : ${response.body()?.posts?.get(0)?.title}")
//            _getPostResponse.value = response.body()?.posts
//            /*       Log.d(
//                       "로그",
//                       "리스폰스 : ${_getPostResponse.value?.get(0)?.title}, 사이즈 : ${_getPostResponse.value?.size}"
//                   )*/
//        }
//
//        override fun onFailure(call: Call<UserGetPostDTO>, t: Throwable) {
//            TODO("Not yet implemented")
//        }
//
//    })

}