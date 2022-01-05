package com.study.bamboo.data.repository.remote

import com.study.bamboo.data.network.models.admin.SetStatusRequest
import com.study.bamboo.data.network.user.AdminApi
import javax.inject.Inject


class AdminRepository @Inject constructor(
    private val adminApi: AdminApi
) {
    suspend fun deletePost( token:String,reason: HashMap<String, String>, id: String) =
        adminApi.deletePost(token ,arg = id, reason)

    suspend fun patchPost(token: String, id: String, request: SetStatusRequest) =
        adminApi.patchPost(token, id, request)

    suspend fun acceptPatchPost(
        token: String,
        id: String,
        bodyMap: HashMap<String, String>,

    ) =
        adminApi.acceptPatchPost(token, id, bodyMap)

    suspend fun transferAdminLogin(password: HashMap<String, String>) = adminApi.transferAdminLogin(password)




}