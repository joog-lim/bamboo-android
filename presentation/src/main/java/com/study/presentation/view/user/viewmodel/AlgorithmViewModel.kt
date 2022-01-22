package com.study.presentation.view.user.viewmodel

import com.study.domain.model.user.response.GetVerifyEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.study.domain.model.admin.response.PostEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.request.VerifierEntity
import com.study.domain.usecease.common.GetAlgorithmUseCase
import com.study.domain.usecease.user.AlgorithmUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlgorithmViewModel @Inject constructor(
    private val getAlgorithmUseCase: GetAlgorithmUseCase,
    private val algorithmUserUserUseCase: AlgorithmUserUseCase

) : ViewModel() {
    private lateinit var _isSuccess: MutableLiveData<Boolean>
    val isSuccess: LiveData<Boolean> get() = _isSuccess
    private val compositeDisposable = CompositeDisposable()


    private lateinit var _isFailure: MutableLiveData<String>
    val isFailure: LiveData<String> get() = _isFailure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private lateinit var _getVerifyResponse: MutableLiveData<GetVerifyEntity>

    val getVerifyResponse: LiveData<GetVerifyEntity> get() = _getVerifyResponse
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
                    if (response.status in 200..299) {
                        _isSuccess.value = true
                        response.data.id
                    } else {
                        _isFailure.value = response.message
                    }
                }, {
                    _isFailure.value = it.message
                }
            )
    }


    fun callGetVerify() = viewModelScope.launch {
        algorithmUserUserUseCase.getVerify()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->

                    if (response.status in 200..299) {
                        _getVerifyResponse.value = response.data
                    } else {

                        _isFailure.value = response.message

                    }
                }, {
                    _isFailure.value = it.message
                }

            )
    }

    fun report(id: String?, body: Report) = viewModelScope.launch {
        if (id != null) {
            algorithmUserUserUseCase.patchAlgorithmReport(id, body)
        }
    }

    fun getAlgorithm(token: String, status: String): Flow<PagingData<PostEntity>> {
        return getAlgorithmUseCase.getAlgorithmPagingSource(token, status)
            .cachedIn(viewModelScope)
    }
}