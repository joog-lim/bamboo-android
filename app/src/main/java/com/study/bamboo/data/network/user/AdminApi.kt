package com.study.bamboo.data.retrofit

import com.study.bamboo.data.network.models.admin.*
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {


    //게시물 삭제
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "post/{id}/delete", hasBody = true)
    suspend fun deletePost(
        @Header("Authorization") Authorization: String,
        @Path("id") arg: String,
        @Field("reason") reason: String,
    ): Response<DeletePostDto>

    //게시물 상태 수정 가능(수락 상태, 거절 상태 등)
    @POST("post/{id}/setStatus")
    suspend fun patchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Body status: HashMap<String, String>,
    ): Response<UpdateStatus>
    // 수락 수정

    @PATCH("post/{id}/modify")
    suspend fun acceptPatchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Body bodyMap: Map<String, String>,

    ): Response<AcceptModify>


    @GET("post/AlgorithemList")
    suspend fun getAcceptPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<AcceptPost>

    @GET("post/AlgorithemList")
    suspend fun getPendingPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<PendingPost>

    @GET("post/AlgorithemList")
    suspend fun getDeletePost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<DeletePost>

    @GET("post/AlgorithemList")
    suspend fun getRejectPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<AdminRejectPost>

    //각각의 status 게시물 가져오기
    @GET("post/count")
    suspend fun getCount(
        @Header("Authorization") Authorization: String,
    ): Response<PostCount>
}