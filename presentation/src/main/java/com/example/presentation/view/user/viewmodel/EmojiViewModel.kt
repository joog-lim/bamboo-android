package com.example.presentation.view.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.user.request.EmojiEntity
import com.example.domain.usecease.user.EmojiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmojiViewModel @Inject constructor(
    private val emojiUseCase: EmojiUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var _isSuccess: MutableLiveData<Boolean>
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private lateinit var _isFailure: MutableLiveData<Boolean>
    val isFailure: LiveData<Boolean> get() = _isFailure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    fun postEmoji(token: String, emoji: EmojiEntity) = viewModelScope.launch {
        emojiUseCase.postEmoji(token, emoji)
            .observeOn(AndroidSchedulers.mainThread()) // 메인쓰레드에 동작
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.status in 200..299) {
                        _isSuccess.value = true
                        _isLoading.postValue(false)

                    } else {
                        _isFailure.value = false
                        _isLoading.postValue(false)
                    }
                }, {
                    _isFailure.value = false
                    _isLoading.postValue(false)
                }

            ).apply {
                compositeDisposable.add(this)
            }


    }


    fun deleteEmoji(token: String, emoji: EmojiEntity) = viewModelScope.launch {
        emojiUseCase.deleteEmoji(token, emoji)
            .observeOn(AndroidSchedulers.mainThread()) // 메인쓰레드에 동작
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.status in 200..299) {
                        _isSuccess.value = true
                        _isLoading.postValue(false)

                    } else {
                        _isFailure.value = false
                        _isLoading.postValue(false)
                    }
                }, {
                    _isFailure.value = false
                    _isLoading.postValue(false)
                }
            ).apply {
                compositeDisposable.add(this)
            }


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


