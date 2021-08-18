package com.study.bamboo.view.fragment.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.model.dto.DeletePostDto
import com.study.bamboo.model.dto.UserGetPostDTO
import com.study.bamboo.model.dto.UserPostDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _deletePostData = MutableLiveData<DeletePostDto>()
    val deletePostDto: LiveData<DeletePostDto> get() = _deletePostData

    private val _getPostData = MutableLiveData<List<UserPostDTO>>()
    val getPostData: LiveData<List<UserPostDTO>> get() = _getPostData

    fun deletePost(arg: String) = viewModelScope.launch {

        adminRepository.deletePost(arg).let { response ->

            if (response.isSuccessful) {
                _deletePostData.value = response.body()
            }
        }
    }

    fun getPost(token:String,count: Int, cursor: String, status: String) = viewModelScope.launch {

        adminRepository.getPost(token,count, cursor, status).let { response ->

            if (response.isSuccessful) {

                response.body()?.posts?.filter { it.status == status }.apply {
                    _getPostData.value = this
                }

            }
        }
    }

}
