package com.study.bamboo.view.activity.main

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
import com.study.bamboo.data.repository.UserRepository
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

    val pagingData = userRepository.getPagingData().cachedIn(viewModelScope)

    val getPostResponse: LiveData<List<UserPostDTO>?> get() = _getPostResponse
    private val _getPostResponse = MutableLiveData<List<UserPostDTO>?>()

    val getCountResponse: LiveData<GetCount> get() = _getCountResponse
    private val _getCountResponse = MutableLiveData<GetCount>()

    val getVerifyResponse: LiveData<GetVerifyDTO> get() = _getVerifyResponse
    private val _getVerifyResponse = MutableLiveData<GetVerifyDTO>()

    //게시물 가져오는 API
    fun callGetPost( page: Int, status: String) = viewModelScope.launch {
        userRepository.getPost(page, status).let { response ->
            if (response.isSuccessful) {
                _getPostResponse.value = response.body()?.posts
            }
        }
    }

    fun setGetPostResponse(getPostResponse :List<UserPostDTO>? ){
        _getPostResponse.value = getPostResponse
    }

/*    fun callGetVerify() = viewModelScope.launch {
        userRepository.getVerify().let { response ->
            if (response.isSuccessful) {
                _getVerifyResponse.value = response.body()
            }
        }
    }*/
}