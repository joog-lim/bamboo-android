package com.study.bamboo.view.fragment.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.admin.AcceptModify
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


    private val _patchPostData = MutableLiveData<AcceptModify>()
    val patchPostDto: LiveData<AcceptModify> get() = _patchPostData

    private val _successData = MutableLiveData<Boolean>()
    val successData: LiveData<Boolean> get() = _successData

    private val _cursor = MutableLiveData<String>()
    val cursor: LiveData<String> get() = _cursor

    init {
        _successData.value = false
        _cursor.value=""
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
                _patchPostData.value = response.body()
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
            } else {
                Log.d(TAG, "deletePost: ${response.errorBody()}")
            }
        }
    }

//    fun getPost(token: String, count: Int, cursor: String,status:String) = viewModelScope.launch {
//        repository.remote.getAcceptPost(token, count, cursor, status).let { response ->
//
//
//            if (response.isSuccessful) {
//                response.body()?.posts?.filter {it.status==status  }.apply {
//                    _cursor.value = response.body()?.cursor
//                }
//            } else {
//                Log.d(TAG, "deletePost: ${response.errorBody()}")
//            }
//        }
//    }


}
