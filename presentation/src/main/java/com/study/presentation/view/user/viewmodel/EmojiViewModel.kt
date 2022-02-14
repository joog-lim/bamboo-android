package com.study.presentation.view.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.base.base.base.BaseViewModel
import com.study.domain.model.user.request.EmojiEntity
import com.study.domain.usecease.user.EmojiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmojiViewModel @Inject constructor(
    private val emojiUseCase: EmojiUseCase
) : BaseViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> get() = _isFailure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    fun postEmoji(token: String, emoji: EmojiEntity) = viewModelScope.launch {
        emojiUseCase.postEmoji(token, emoji)
            .observeOn(AndroidSchedulers.mainThread()) // 메인쓰레드에 동작
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.success) {
                        _isSuccess.value = true
                        _isLoading.postValue(false)

                    } else {
                        _isFailure.value = response.message
                        _isLoading.postValue(false)
                    }
                }, {
                    _isFailure.value = it.message
                    _isLoading.postValue(false)
                }

            ).apply {
                addDisposable(this@apply)
            }


    }


    fun deleteEmoji(token: String, emoji: EmojiEntity) = viewModelScope.launch {
        emojiUseCase.deleteEmoji(token, emoji)
            .observeOn(AndroidSchedulers.mainThread()) // 메인쓰레드에 동작
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.success) {
                        _isSuccess.value = true
                        _isLoading.postValue(false)

                    } else {
                        _isFailure.value = response.message
                        _isLoading.postValue(false)
                    }
                }, {
                    _isFailure.value = it.message
                    _isLoading.postValue(false)
                }
            ).apply {
                addDisposable(this@apply)
            }


    }


}


