package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.UserGetPostDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVerifyAPI {
    @GET("get/verify")
    fun getPost(
        @Query("id") id : String,
        @Query("question") question : String
    ) : Call<UserGetPostDTO>
}