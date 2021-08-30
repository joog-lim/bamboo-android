package com.study.bamboo.view.activity.splash

import com.study.bamboo.data.network.models.user.postcreate.PostCreateRequest
import com.study.bamboo.data.network.user.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi){
    suspend fun getPost(count : Int, cursor : String, status : String) = userApi.getPost(count, cursor, status)
    suspend fun transferPostCreate(request : PostCreateRequest) = userApi.transferPostCreate(request)
    suspend fun getVerify() = userApi.getVerify()
}