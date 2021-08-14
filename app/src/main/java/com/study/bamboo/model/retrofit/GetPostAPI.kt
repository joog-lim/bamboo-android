package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.GetPostDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPostAPI {
    @GET("post/get-list")
    fun getPost(
        @Query("count") count : Int,
        @Query("cursor") cursor : String,
        @Query("status") status : String
    ) : Call<GetPostDTO>
}