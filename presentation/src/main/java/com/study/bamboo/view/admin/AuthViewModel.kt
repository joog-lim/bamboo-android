package com.study.bamboo.view.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.base.base.base.BaseViewModel
import com.study.data.network.common.CommonApi
import com.study.data.utils.DataStoreManager
import com.study.domain.model.common.LoginEntity
import com.study.domain.usecease.common.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    val api: CommonApi,
    private val dataStore: DataStoreManager
) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> get() = _isFailure

    private val _isLoginResult = MutableLiveData<LoginEntity?>()
    val isLoginResult: LiveData<LoginEntity?> get() = _isLoginResult

    suspend fun getToken(): String {
        return dataStore.getToken()
    }

    fun postLogin(token: String) = viewModelScope.launch {
        _isLoading.postValue(true)


        authUseCase.postLogin(token)
            .subscribe(
                {
                    Log.d("TAG", "postLogin: ${it}")
                    if (it.success) {
                        _isLoading.value = false
                        _isLoginResult.value = it.data
                    } else {
                        _isLoading.value = false
                        _isFailure.value = it.message?:""

                    }
                }, {
                    _isLoading.value = false
                    _isFailure.value = it.message
                    Log.d("TAG", "postLogins: ${it.message}")

                }
            )
    }
}