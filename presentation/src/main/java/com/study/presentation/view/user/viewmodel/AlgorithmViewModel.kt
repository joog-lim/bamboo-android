package com.study.presentation.view.user.viewmodel

import android.util.Log
import com.study.domain.model.user.response.GetVerifyEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.study.base.base.base.BaseViewModel
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.request.VerifierEntity
import com.study.domain.usecease.user.AlgorithmUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlgorithmViewModel @Inject constructor(
    private val algorithmUserUserUseCase: AlgorithmUserUseCase,

    ) : BaseViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess
    private val compositeDisposable = CompositeDisposable()


    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> get() = _isFailure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _getVerifyResponse = MutableLiveData<GetVerifyEntity?>()

    val getVerifyResponse: LiveData<GetVerifyEntity?> get() = _getVerifyResponse
    fun callPostCreateAPI(
        title: String,
        content: String,
        tag: String,
        questionId: String,
        answer: String
    ) = viewModelScope.launch {

        algorithmUserUserUseCase.algorithmCreate(
            AlgorithmCreate(
                title, content, tag,
                VerifierEntity(questionId, answer)
            )
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.success) {
                        _isSuccess.value = true
                        response.data?.id
                    } else {
                        _isFailure.value = response.message!!
                    }
                }, {
                    _isFailure.value = it.message
                }
            )
    }


    fun callGetVerify() = viewModelScope.launch {
        _isLoading.postValue(true)
        algorithmUserUserUseCase.getVerify()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->

                    if (response.success) {
                        Log.d("TAG", "callGetVerify: ${response.data?.id}")
                        _isLoading.postValue(false)

                        _getVerifyResponse.value = response.data
                    } else {
                        _isLoading.postValue(false)

                        _isFailure.value = response.message!!

                    }
                }, {
                    _isLoading.postValue(false)
                    _isFailure.value = it.message
                }

            )
    }



    fun getAlgorithm(token: String): Flow<PagingData<ResultEntity>> {
        return algorithmUserUserUseCase.getAlgorithmPagingSource(token)
            .cachedIn(viewModelScope)
    }
}