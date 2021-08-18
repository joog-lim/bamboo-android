package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.DeletePostDto
import com.study.bamboo.model.dto.UserGetPostDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {
    @DELETE("delete/{arg}")
   suspend fun deletePost(
        @Path("arg") arg: String
    ): Response<DeletePostDto>

    @GET("post/get-list")
  suspend  fun getPost(
        @Header("Authorization") Authorization :String,
        @Query("count") count : Int,
        @Query("cursor") cursor : String,
        @Query("status") status : String
    ) : Response<UserGetPostDTO>
}