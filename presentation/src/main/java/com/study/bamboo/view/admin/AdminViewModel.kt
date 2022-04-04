package com.study.bamboo.view.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.study.base.base.base.BaseViewModel
import com.study.domain.model.admin.request.AlgorithmModifyEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.usecease.admin.AlgorithmAdminUseCase
import com.study.domain.usecease.common.StatusUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(

    private val algorithmAdminUseCase: AlgorithmAdminUseCase,
    private val statusUpdateUseCase: StatusUpdateUseCase

    ) : BaseViewModel() {


    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> get() = _isSuccess

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> get() = _isFailure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading


    fun acceptPatchPost(
        token: String,
        id: Int,
        bodyMap: AlgorithmModifyEntity

    ) = viewModelScope.launch {
        _isLoading.postValue(true)
        algorithmAdminUseCase.patchModifyAlgorithm(token, id.toString(), bodyMap)
            .observeOn(AndroidSchedulers.mainThread()) // 메인쓰레드에 동작
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.success) {
                        _isSuccess.value = "성공적으로 수정했습니다."
                        _isLoading.postValue(false)

                    } else {
                        _isFailure.value = "수정하지 못했습니다. 에러 메세지 :  ${response.message}"
                        _isLoading.postValue(false)
                    }
                }, {
                    _isFailure.value = "수정하지 못했습니다. 에러 메세지 :  ${it.message}"
                    _isLoading.postValue(false)
                }
            )

    }


    fun patchPost(token: String, id:  Int, request: SetStatusEntity) =
        viewModelScope.launch {
            Log.d("TAG", "patchPost: ")
            _isLoading.postValue(true)
            statusUpdateUseCase.patchStatusAlgorithm(token, id.toString(), request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->

                        if (response.success) {
                            _isSuccess.value = "$request 상태를 성공적으로 수정했습니다."
                            _isLoading.postValue(false)

                        } else {
                            _isFailure.value = "수정하지 못했습니다. 에러 메세지 :  ${response.message}"
                            _isLoading.postValue(false)
                        }
                    }, {
                        _isFailure.value = "수정하지 못했습니다. 에러 메세지 :  ${it.message}"
                        _isLoading.postValue(false)
                    }
                )


        }


    fun deletePost(token: String, id: Int) =
        viewModelScope.launch {
            _isLoading.postValue(false)
            algorithmAdminUseCase.deleteAlgorithm(token, id.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        if (response.success) {
                            _isSuccess.value = "성공적으로 수정했습니다."
                            _isLoading.postValue(false)
                        } else {
                            _isFailure.value = "삭제하지 못했습니다. 에러 메세지 :  ${response.message}"
                            _isLoading.postValue(false)
                        }
                    }, {
                        _isFailure.value = "삭제하지 못했습니다 에러 메세지 :  ${it.message}"
                        _isLoading.postValue(false)
                    }
                )

        }


    fun getAlgorithm(token: String, status: String): Flow<PagingData<ResultEntity>> {
        return algorithmAdminUseCase.getAlgorithmPagingSource(token, status)
            .cachedIn(viewModelScope)
    }


}
