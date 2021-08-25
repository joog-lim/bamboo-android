package com.study.bamboo.model.retrofit

import com.study.bamboo.adapter.Status
import com.study.bamboo.model.dto.DeletePostDto
import com.study.bamboo.model.dto.PatchDto
import com.study.bamboo.model.dto.UserGetPostDTO
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {
    //게시물 삭제
    @DELETE("post/delete/{arg}")
    suspend fun deletePost(
        @Header("Authorization") Authorization: String,
        @Path("arg") arg: String,
        @Query("message") message:String,
    ): Response<DeletePostDto>

    //게시물 상태 수정 가능(수락 상태, 거절 상태 등)
    @FormUrlEncoded
    @PATCH("post/patch/{id}")
    suspend fun patchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Field("status") status:Status,
        @Field("title") title:String,
        @Field("content") content:String,
        @Field("reason") reason:String,
    ): Response<PatchDto>




    @GET("post/get-list")
    suspend fun getPost(
        @Header("Authorization") Authorization: String,
        @Query("count") count: Int,
        @Query("cursor") cursor: String,
        @Query("status") status: String
    ): Response<UserGetPostDTO>
}