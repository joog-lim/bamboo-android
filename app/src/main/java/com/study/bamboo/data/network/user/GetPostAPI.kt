package com.study.bamboo.data.retrofit

import com.study.bamboo.data.network.models.user.UserGetPostDTO
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