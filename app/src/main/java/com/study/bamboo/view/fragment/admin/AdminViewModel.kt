package com.study.bamboo.view.fragment.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.admin.AcceptModify
import com.study.bamboo.data.repository.Repository
import com.study.bamboo.utils.Admin
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
                Log.d(TAG, "acceptPatchPost: 성공")

            }else{
                Log.d(TAG, "acceptPatchPost: ${response.message()}")
            }
        }
    }

    fun patchPost(token: String, id: String, status: HashMap<String, String>) =
        viewModelScope.launch {
            repository.remote.patchPost(token, id, status).let {

                if (it.isSuccessful) {

                    Log.d(TAG, "patchPost: 성공!")
                }

                Log.d(TAG, "patchPost: ${it.errorBody()}")
            }
        }

    fun deletePost(token: String, reason: String, id: String) = viewModelScope.launch {

        repository.remote.deletePost(token, reason, id).let { response ->

            if (response.isSuccessful) {
            } else {
                Log.d(TAG, "deletePost: ${response.errorBody()}")
            }
        }
    }






}
