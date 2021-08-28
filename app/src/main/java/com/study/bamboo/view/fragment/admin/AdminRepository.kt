package com.study.bamboo.view.fragment.admin

import com.study.bamboo.model.retrofit.AdminApi
import javax.inject.Inject


class AdminRepository @Inject constructor(
    private val adminApi: AdminApi
) {
    suspend fun deletePost(token: String, reason: String, id: String) =
        adminApi.deletePost(token, id, reason)

    suspend fun patchPost(token: String, id: String, status: String) =
        adminApi.patchPost(token, id, status)

    suspend fun acceptPatchPost(
        token: String,
        id: String,
        title: String,
        content: String,
        reason: String
    ) =
        adminApi.acceptPatchPost(token, id, title, content, reason)


}