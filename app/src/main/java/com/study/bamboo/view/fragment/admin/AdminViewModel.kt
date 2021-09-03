package com.study.bamboo.view.fragment.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    private val _cursor = MutableLiveData<String>()
    val cursor: LiveData<String> get() = _cursor

    private val _successPatchData = MutableLiveData<String?>()
    val successPatchData: MutableLiveData<String?> get() = _successPatchData

    private val _successAcceptData = MutableLiveData<String?>()
    val successAcceptData: MutableLiveData<String?> get() = _successAcceptData

    private val _successDeleteData = MutableLiveData<String?>()
    val successDeleteData: MutableLiveData<String?> get() = _successDeleteData


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

            var number = response.body()?.number
            if (response.isSuccessful) {
                Log.d(TAG, "acceptPatchPost: 성공")
                _successAcceptData.value = "${number}번째 게시물을 수정했습니다."

            } else {
                _successAcceptData.value = " ${response.message()}"
            }
        }
    }

    fun patchPost(token: String, id: String, status: HashMap<String, String>) =
        viewModelScope.launch {
            repository.remote.patchPost(token, id, status).let { response ->

                val beforeStatus = response.body()?.beforeStatus
                val afterStatus = response.body()?.afterStatus
                if (response.isSuccessful) {
                    _successPatchData.value = "$beforeStatus status 를 $afterStatus 로 바꾸었습니다."

                    Log.d(TAG, "patchPost: 성공!")
                }else{
                    _successPatchData.value=response.message()
                }

            }
        }

    fun deletePost(token: String, reason: String, id: String) = viewModelScope.launch {

        repository.remote.deletePost(token, reason, id).let { response ->
            var number = response.body()?.number
            if (response.isSuccessful) {
                _successDeleteData.value = "$number 의 게시물을 제거했습니다."
            } else {
                Log.d(TAG, "deletePost: ${response.errorBody()}")
            }
        }
    }


}
