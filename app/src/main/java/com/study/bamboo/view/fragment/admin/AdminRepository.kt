package com.study.bamboo.view.fragment.admin

import com.study.bamboo.model.dto.AdminSignInDTO
import com.study.bamboo.model.retrofit.AdminApi
import javax.inject.Inject


class AdminRepository @Inject constructor(
    private val adminApi: AdminApi
) {

    suspend fun deletePost(arg: String) = adminApi.deletePost(arg)
    suspend fun getPost(token:String,count: Int, cursor: String, status: String) =
        adminApi.getPost(token,count, cursor, status)
}