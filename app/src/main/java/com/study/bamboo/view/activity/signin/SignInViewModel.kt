package com.study.bamboo.view.activity.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.user.UserPostDTO
import com.study.bamboo.data.network.models.user.getcount.GetCount
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import com.study.bamboo.data.repository.remote.AdminRepository
import com.study.bamboo.view.activity.splash.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository
) : ViewModel() {


    private val _adminLoginResponse = MutableLiveData<String>()
    val adminLoginResponse: LiveData<String> get() = _adminLoginResponse

    val getPostResponse: LiveData<List<UserPostDTO>?> get() = _getPostResponse
    private val _getPostResponse = MutableLiveData<List<UserPostDTO>?>()

    val getCountResponse: LiveData<Int> get() = _getCountResponse
    private val _getCountResponse = MutableLiveData<Int>()


    val success = MutableLiveData<Boolean>()

    init {
        success.value = false
    }


    //관리자 로그인 API
    fun callAdminLoginAPI(password: String) {
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

    //게시물 가져오는 API
    fun callGetPost(count: Int, cursor: String, status: String) = viewModelScope.launch {
        userRepository.getPost(count, cursor, status).let { response ->
            if (response.isSuccessful) {
                _getPostResponse.value = response.body()?.posts
            }
        }
    }

    //게시물 크기 가져오는 API
    fun callGetCount() = viewModelScope.launch {
        userRepository.getCount().let { response ->
            val count = findAccepted(response)
            _getCountResponse.value = count
        }
    }


    fun findAccepted(response: Response<GetCount>): Int {
        var count = 0
        if (response != null) {
            for (get in 0..3) {
                if (response.body()?.get(get)?._id == "ACCEPTED") {
                    count = response.body()!!.get(get).count
                }
            }
        }

        return count
    }


}