package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.UserGetPostDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPostAPI {
    @GET("post/AlgorithemList")
    fun getPost(
        @Query("count") count : Int,
        @Query("cursor") cursor : String,
        @Query("status") status : String
    ) : Call<UserGetPostDTO>
}