package com.study.bamboo.view.fragment.admin

import com.study.bamboo.adapter.Status
import com.study.bamboo.model.retrofit.AdminApi
import javax.inject.Inject


class AdminRepository @Inject constructor(
    private val adminApi: AdminApi
) {
    suspend fun deletePost(token:String,message: String, arg: String) = adminApi.deletePost(token,message, arg)

    suspend fun patchPost(token:String,id: String, status:Status,title: String, content: String, reason: String) =
        adminApi.patchPost(token,id, status,title, content, reason)

    suspend fun getPost(token: String, count: Int, cursor: String, status: String) =
        adminApi.getPost(token, count, cursor, status)


}