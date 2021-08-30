package com.study.bamboo.view.fragment.admin

import android.util.Log
import androidx.lifecycle.*
import com.study.bamboo.data.network.models.admin.AcceptModify
import com.study.bamboo.data.network.models.admin.DeletePostDto
import com.study.bamboo.data.repository.Repository

import com.study.bamboo.utils.Util.Companion.DEFAULT_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: Repository

) : ViewModel() {

    var token = DEFAULT_TOKEN
    val readToken = repository.dataStore.readToken
    private val TAG = "AdminViewModel"

    private val _deletePostData = MutableLiveData<DeletePostDto>()
    val deletePostDtoDto: LiveData<DeletePostDto> get() = _deletePostData

    private val _patchPostData = MutableLiveData<AcceptModify>()
    val patchPostDto: LiveData<AcceptModify> get() = _patchPostData

    private val _successData = MutableLiveData<Boolean>()
    val successData: LiveData<Boolean> get() = _successData

    init {
        _successData.value = false
    }

    // 토큰을 저장한다.
    fun saveToken(token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("AdminViewModel", "saveToken: $token")
            repository.dataStore.saveToken(token)
        }


    suspend fun acceptPatchPost(
        token: String,
        id: String,
        bodyMap: HashMap<String, String>

    ) = viewModelScope.launch {
        repository.remote.acceptPatchPost(token, id, bodyMap).let { response ->

            if (response.isSuccessful) {
                _successData.value = true
                _patchPostData.value=response.body()
            }
        }
    }

    fun patchPost(token: String, id: String, status: HashMap<String, String>) =
        viewModelScope.launch {
            repository.remote.patchPost(token, id, status).let {

                if (it.isSuccessful) {
                    Log.d(TAG, "patchPost: 성공!")
                    _successData.value = true
                }
                it.errorBody()
            }
        }

    fun deletePost(token: String, reason: String, id: String) = viewModelScope.launch {

        repository.remote.deletePost(token, reason, id).let { response ->

            if (response.isSuccessful) {
                _deletePostData.value = response.body()
            } else {
                Log.d(TAG, "deletePost: ${response.errorBody()}")
            }
        }
    }


}
