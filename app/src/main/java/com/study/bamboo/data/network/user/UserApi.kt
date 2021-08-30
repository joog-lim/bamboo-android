package com.study.bamboo.data.network.user


import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.UserGetPostDTO
import com.study.bamboo.data.network.models.user.postcreate.PostCreateRequest
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("post/AlgorithemList")
    fun getPost(
        @Query("count") count : Int,
        @Query("cursor") cursor : String,
        @Query("status") status : String
    ) : Call<UserGetPostDTO>

    @POST("post/create")
    fun transferPostCreate(
        @Body request : PostCreateRequest
    ): Call<PostCreateResponse>

    @GET("verify")
    fun getVerify() : Call<GetVerifyDTO>
}