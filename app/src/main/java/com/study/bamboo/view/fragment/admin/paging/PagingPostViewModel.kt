package com.study.bamboo.view.fragment.admin.paging

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.model.retrofit.AdminApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PagingPostViewModel @Inject constructor(
    application: Application,
    private val adminApi: AdminApi
) : AndroidViewModel(application) {


    private val postData = MutableLiveData<UserPostDTO>()
    val token = MutableLiveData<String>()
    val cursor = MutableLiveData<String>()
    val status = MutableLiveData<String>()


    fun getData(token: String, cursor: String, status: String) {
        this.token.value = token
        this.cursor.value = cursor
        this.status.value = status
        Log.d(TAG, "token : $token cursor : $cursor status : $status")
    }

//    val photos = postData.switchMap { queryString ->
//        postPagingRepository.getPost(token.value.toString(), cursor.value, status = status.value.toString())
//            .cachedIn(viewModelScope)
//    }


//    suspend fun getPost(
//        token: String, cursor: String, status: String
//    ) {
//        val result = postPagingRepository.getPost(token, cursor, status)
//            .cachedIn(viewModelScope)
//        Log.d(TAG, "getPost: $result")
//        _postResult.value = result.value
//    }


    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = UNSPLASH_STARTING_PAGE_INDEX,
            enablePlaceholders = false
        )
    ) {
        PostPagingSource(
            adminApi,
            token.value.toString(),
            cursor.value.toString(),
            status.value.toString()
        )
    }.flow
        .cachedIn(viewModelScope)


    companion object {
        const val TAG = "PagingPostViewModel"
    }


}