package com.study.bamboo.data.network.user


import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.UserGetPostDTO
import com.study.bamboo.data.network.models.user.getcount.GetCount
import com.study.bamboo.data.network.models.user.postcreate.PostCreateRequest
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("post/AlgorithemList")
    suspend fun getPost(
        @Query("count") count : Int,
        @Query("cursor") cursor : String,
        @Query("status") status : String
    ) : Response<UserGetPostDTO>

    @POST("post/create")
    suspend fun transferPostCreate(
        @Body request : PostCreateRequest
    ): Response<PostCreateResponse>

    @GET("verify")
    suspend fun getVerify() : Response<GetVerifyDTO>

    @GET("post/count")
    suspend fun getCount() : Response<GetCount>
}