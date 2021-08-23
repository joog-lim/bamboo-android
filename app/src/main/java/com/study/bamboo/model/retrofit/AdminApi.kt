package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.DeletePostDto
import com.study.bamboo.model.dto.PatchPostDto
import com.study.bamboo.model.dto.UserGetPostDTO
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {
    //게시물 삭제
    @DELETE("delete/{arg}")
    suspend fun deletePost(
        @Path("arg") arg: String
    ): Response<DeletePostDto>

    //게시물 상태 수정 가능(수락 상태, 거절 상태 등)
    @PATCH("patch/{id}")
    suspend fun patchPost(
        @Path("id") id: String
    ): Response<PatchPostDto>

    //승인 대기 상태 게시물
    @GET("post/get-list")
    suspend fun getPendingPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String,
        @Query("status") status: String
    ): Response<UserGetPostDTO>

    //게시된 상태 게시물
    @GET("post/get-list")
    suspend fun getAcceptedPost(
        @Query("count") count: Int,
        @Query("cursor") cursor: String,
        @Query("status") status: String
    ): Response<UserGetPostDTO>

    //이미 거절된 상태 게시물
    @GET("post/get-list")
    suspend fun getRejectedPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String,
        @Query("status") status: String
    ): Response<UserGetPostDTO>

    //누군가 삭제요청(신고)를 보낸 상태 게시물
    @GET("post/get-list")
    suspend fun getDeletedPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String,
        @Query("status") status: String
    ): Response<UserGetPostDTO>
}