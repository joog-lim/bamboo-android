package com.example.data.network.admin

import com.example.data.model.admin.request.AlgorithmModifyRequest
import com.example.data.model.admin.request.SetStatusRequest
import com.example.data.model.admin.response.AlgorithmResponse
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {


    //게시물 상태 수정 (수락 상태, 거절 상태 등)
    @PATCH("algorithm/{id}/status")
    suspend fun patchPost(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body body: SetStatusRequest,
    ): Response<Void>


    // 게시물 수정
    // id는 게시물 고유 index 값
    @PATCH("algorithm/{id}")
    suspend fun acceptPatchPost(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body body: AlgorithmModifyRequest,
    ): Response<Void>

    //게시물 삭제
    @HTTP(method = "DELETE", path = "algorithm/{id}", hasBody = true)
    suspend fun deletePost(
        @Header("Authorization") authorization: String,
        @Path("id") arg: String,
    ): Response<Void>

    // 게시물 페이지로 조히
    @GET("algorithm/page")
    suspend fun getAlgorithmPage(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int,
        @Query("page") page: Int,
        @Query("status") status: String
    ): Response<AlgorithmResponse>


}