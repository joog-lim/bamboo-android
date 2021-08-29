package com.study.bamboo.view.fragment.admin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.bamboo.datastore.DataStoreRepository
import com.study.bamboo.model.dto.admin.AcceptModify
import com.study.bamboo.model.dto.admin.DeletePostDto

import com.study.bamboo.utils.Util.Companion.DEFAULT_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val TAG = "AdminViewModel"

    private val _deletePostData = MutableLiveData<DeletePostDto>()
    val deletePostDtoDto: LiveData<DeletePostDto> get() = _deletePostData

    private val _patchPostData = MutableLiveData<AcceptModify>()
    val patchPostDto: LiveData<AcceptModify> get() = _patchPostData

    private val _successData = MutableLiveData<Boolean>()
    val successData: LiveData<Boolean> get() = _successData
init{
    _successData.value=false
}

    // 토큰을 저장한다.
    fun saveToken(token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("AdminViewModel", "saveToken: $token")
            dataStoreRepository.saveToken(token)
        }


    fun acceptPatchPost(
        token: String,
        id: String,
        title: String,
        content: String,
        tag: String
    ) = viewModelScope.launch {
        adminRepository.acceptPatchPost(token, id, title, content, tag).let { response ->

            if (response.isSuccessful) {
                _successData.value = true
            }
        }
    }

    fun patchPost(token: String, id: String, status: HashMap<String, String>) = viewModelScope.launch {
        adminRepository.patchPost(token, id, status).let{

            if(it.isSuccessful){
                Log.d(TAG, "patchPost: 성공!")
                _successData.value = true
            }
            it.errorBody()
        }
    }

    fun deletePost(token: String, reason: String, id: String) = viewModelScope.launch {

        adminRepository.deletePost(token, reason, id).let { response ->

            if (response.isSuccessful) {
                _deletePostData.value = response.body()
            } else {
                Log.d(TAG, "deletePost: ${response.errorBody()}")
            }
        }
    }


}
