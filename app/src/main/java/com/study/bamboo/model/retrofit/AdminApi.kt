package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.DeletePostDto
import com.study.bamboo.model.dto.UserGetPostDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AdminApi {
    @DELETE("delete/{arg}")
   suspend fun deletePost(
        @Path("arg") arg: String
    ): Response<DeletePostDto>

    @GET("post/get-list")
  suspend  fun getPost(
        @Query("count") count : Int,
        @Query("cursor") cursor : String,
        @Query("status") status : String
    ) : Response<UserGetPostDTO>
}