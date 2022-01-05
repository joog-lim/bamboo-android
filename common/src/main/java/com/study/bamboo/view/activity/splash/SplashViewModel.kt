package com.study.bamboo.view.activity.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.repository.UserRepository
import com.study.bamboo.data.network.models.user.GetVerifyDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    //verify 값 받아오기
    val getVerifyResponse: LiveData<GetVerifyDTO> get() = _getVerifyResponse
    private val _getVerifyResponse = MutableLiveData<GetVerifyDTO>()

    val errorResponse: LiveData<ResponseBody> get() = _errorResponse
    private val _errorResponse = MutableLiveData<ResponseBody>()

    //verify 호출로 id와 질문 가져오기
    fun callGetVerifyAPI() = viewModelScope.launch {
        userRepository.getVerify().let { response ->
            if (response.isSuccessful)
                _getVerifyResponse.value = response.body()
            else
                _errorResponse.value = response.errorBody()
        }

    }

}