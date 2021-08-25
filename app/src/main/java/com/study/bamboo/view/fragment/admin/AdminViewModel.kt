package com.study.bamboo.view.fragment.admin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.study.bamboo.adapter.Status
import com.study.bamboo.data.DataStoreRepository
import com.study.bamboo.model.dto.DeletePostDto
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.utils.Util.Companion.DEFAULT_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    application: Application,
    private val adminRepository: AdminRepository,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

     var token = DEFAULT_TOKEN
    val readToken = dataStoreRepository.readToken


    private val _deletePostData = MutableLiveData<DeletePostDto>()
    val deletePostDto: LiveData<DeletePostDto> get() = _deletePostData


    private val _getPostData = MutableLiveData<List<UserPostDTO>>()
    val getPostData: LiveData<List<UserPostDTO>> get() = _getPostData

    // 토큰을 저장한다.
    fun saveToken(token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveToken(token)
        }

    // token을 쓴다 ?

    fun getToken() {
        viewModelScope.launch {
            readToken.collect { value ->
                token = value.token
                Log.d("AdminViewModel", "token: ${value.token}")

            }
        }

    }

    fun patchPost(
        token: String,
        id: String,
        status: Status,
        title: String,
        content: String,
        reason: String
    ) = viewModelScope.launch {
        adminRepository.patchPost(token, id, status, title, content, reason).let { response ->

            if (response.isSuccessful) {
            }
        }
    }


    fun deletePost(message: String, arg: String) = viewModelScope.launch {

        adminRepository.deletePost(arg, message).let { response ->

            if (response.isSuccessful) {
                _deletePostData.value = response.body()
            }
        }
    }

    suspend fun getPostData(token: String, count: Int, cursor: String, status: String) =
        viewModelScope.launch {

            adminRepository.getPost(token, count, cursor, status).let { response ->
                response.body()?.posts?.filter { it.status == status }.apply {
                    _getPostData.value = this
                }

            }
        }

}
