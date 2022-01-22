package com.example.presentation.view.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.admin.request.AlgorithmModifyEntity
import com.example.domain.model.admin.request.SetStatusEntity
import com.example.domain.model.admin.response.PostEntity
import com.example.domain.usecease.admin.AlgorithmAdminUseCase
import com.example.domain.usecease.common.GetAlgorithmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(

    private val algorithmAdminUseCase: AlgorithmAdminUseCase,
    private val getAlgorithmUseCase: GetAlgorithmUseCase

) : ViewModel() {



    private val compositeDisposable = CompositeDisposable()
    private lateinit var _isSuccess: MutableLiveData<String>
    val isSuccess: LiveData<String> get() = _isSuccess

    private lateinit var _isFailure: MutableLiveData<String>
    val isFailure: LiveData<String> get() = _isFailure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading



     fun acceptPatchPost(
        token: String,
        id: String,
        bodyMap: AlgorithmModifyEntity

    ) = viewModelScope.launch {
        _isLoading.postValue(true)
        algorithmAdminUseCase.patchModifyAlgorithm(token, id, bodyMap)
            .observeOn(AndroidSchedulers.mainThread()) // 메인쓰레드에 동작
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    if (response.status in 200..299) {
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


    fun patchPost(token: String, id: String, request: SetStatusEntity) =
        viewModelScope.launch {
            _isLoading.postValue(true)
            algorithmAdminUseCase.patchStatusAlgorithm(token, id, request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->

                        if (response.status in 200..299) {
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


    fun deletePost(token: String, id: String) =
        viewModelScope.launch {
            _isLoading.postValue(false)
            algorithmAdminUseCase.deleteAlgorithm(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        if (response.status in 200..299) {
                            _isSuccess.value = "성공적으로 수정했습니다."
                            _isLoading.postValue(false)
                        } else {
                            _isFailure.value = "삭제하지 못했습니다. 에러 메세지 :  ${response.message}"
                            _isLoading.postValue(false)
                        }
                    },{
                        _isFailure.value = "삭제하지 못했습니다 에러 메세지 :  ${it.message}"
                        _isLoading.postValue(false)
                    }
                )

        }


fun getAlgorithm(token: String, status: String): Flow<PagingData<PostEntity>> {
    return getAlgorithmUseCase.getAlgorithmPagingSource(token, status)
        .cachedIn(viewModelScope)
}

override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
}
}
