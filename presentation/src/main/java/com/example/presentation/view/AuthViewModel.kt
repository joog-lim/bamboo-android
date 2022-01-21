package com.example.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.common.LoginEntity
import com.example.domain.usecease.common.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> get() = _isFailure

    private val _isLoginResult = MutableLiveData<LoginEntity>()
    val isLoginResult: LiveData<LoginEntity> get() = _isLoginResult

    fun postLogin(token: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        authUseCase.postLogin(token).subscribe(
            {

                if (it.status in 200..299) {
                    _isLoading.value = false
                    _isLoginResult.value = it.data!!
                } else {
                    _isLoading.value = false

                    _isFailure.value = it.message
                }
            }, {
                _isLoading.value = false

                _isFailure.value = it.message
            }
        )
    }
}