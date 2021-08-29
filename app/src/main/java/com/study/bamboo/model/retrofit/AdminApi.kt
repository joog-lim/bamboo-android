package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.UserGetPostDTO
import com.study.bamboo.model.dto.admin.*
import com.study.bamboo.model.dto.admin.get.AcceptPost
import com.study.bamboo.model.dto.admin.get.AdminRejectPost
import com.study.bamboo.model.dto.admin.get.DeletePost
import com.study.bamboo.model.dto.admin.get.PendingPost
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
        @Body status:  HashMap<String, String>,
    ): Response<UpdateStatus>
    // 수락 수정

    @FormUrlEncoded
    @PATCH("post/{id}/modify")
    suspend fun acceptPatchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("tag") tag: String,
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