package com.study.bamboo.view.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.UserPostDTO
import com.study.bamboo.data.network.models.user.getcount.GetCount
import com.study.bamboo.data.network.user.UserApi
import com.study.bamboo.view.activity.splash.UserRepository
import com.study.bamboo.data.paging.GetPostSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userApi: UserApi
) : ViewModel() {

    val getPostResponse: LiveData<List<UserPostDTO>?> get() = _getPostResponse
    private val _getPostResponse = MutableLiveData<List<UserPostDTO>?>()

    val getCountResponse: LiveData<GetCount> get() = _getCountResponse
    private val _getCountResponse = MutableLiveData<GetCount>()

    val getVerifyResponse: LiveData<GetVerifyDTO> get() = _getVerifyResponse
    private val _getVerifyResponse = MutableLiveData<GetVerifyDTO>()

    //게시물 가져오는 API
    fun callGetPost(count: Int, cursor: String, status: String) = viewModelScope.launch {
        userRepository.getPost(count, cursor, status).let { response ->
            if (response.isSuccessful) {
                _getPostResponse.value = response.body()?.posts
            }
        }
    }

/*    fun callGetCount() = viewModelScope.launch {
        userRepository.getCount().let { response ->
            if (response.isSuccessful) {
                _getCountResponse.value = response.body()
                findAccepted(response)
            }
        }
    }*/

    fun getListData(count: Int): Flow<PagingData<UserPostDTO>> {

        return Pager(
            PagingConfig(pageSize = 37)
        ) {
            GetPostSource(userApi)
        }.flow.cachedIn(viewModelScope)

    }

    fun callGetVerify() = viewModelScope.launch {
        userRepository.getVerify().let { response ->
            if (response.isSuccessful) {
                _getVerifyResponse.value = response.body()
            }
        }
    }

/*    fun findAccepted(response: GetCount?): Int {
        var count = 0
        if (response.body() != null) {
            for (get in 0..(response.body()?.size ?: 0)) {
                if (response.body()?.get(get)?._id ?: 0 == "ACCEPTED") {
                    count = response.body()!!.get(get).count
                }
            }
        }

        return count
    }*/

}