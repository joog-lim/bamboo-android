package com.study.bamboo.view.fragment.admin

import com.study.bamboo.model.retrofit.AdminApi
import javax.inject.Inject


class AdminRepository @Inject constructor(
    private val adminApi: AdminApi
) {
    suspend fun deletePost(token:String,message: String, arg: String) = adminApi.deletePost(token,message, arg)

    suspend fun patchPost(token:String,id: String, status:String,title: String, content: String, reason: String) =
        adminApi.patchPost(token,id, status,title, content, reason)
    suspend fun acceptPatchPost(token:String,id: String,title: String, content: String, reason: String) =
        adminApi.acceptPatchPost(token,id,title, content, reason)



}