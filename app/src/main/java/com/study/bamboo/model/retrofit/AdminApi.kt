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
//    @DELETE("/post/delete/{id}")
    @HTTP(method = "DELETE", path = "/post/delete/{id}", hasBody = true)
    suspend fun deletePost(
        @Header("Authorization") Authorization: String,
        @Path("id") arg: String,
        @Field("reason") reason: String,
    ): Response<DeletePostDto>

    //게시물 상태 수정 가능(수락 상태, 거절 상태 등)
    @FormUrlEncoded
    @PATCH("post/patch/{id}")
    suspend fun patchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Field("status") status:String,

    ): Response<UpdateStatus>
    // 수락 수정

    @FormUrlEncoded
    @PATCH("post/patch/{id}")
    suspend fun acceptPatchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Field("title") title:String,
        @Field("content") content:String,
        @Field("reason") reason:String,
    ): Response<AcceptModify>



    @GET("post/get-list")
    suspend fun getPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<UserGetPostDTO>

    @GET("post/get-list")
    suspend fun getAcceptPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<AcceptPost>

    @GET("post/get-list")
    suspend fun getPendingPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<PendingPost>

    @GET("post/get-list")
    suspend fun getDeletePost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<DeletePost>

    @GET("post/get-list")
    suspend fun getRejectPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String?,
        @Query("status") status: String
    ): Response<AdminRejectPost>
}