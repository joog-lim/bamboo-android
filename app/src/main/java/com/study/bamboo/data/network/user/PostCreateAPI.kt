package com.study.bamboo.data.retrofit

import com.study.bamboo.data.network.models.user.AdminSignInDTO
import com.study.bamboo.data.network.models.user.postcreate.PostCreateRequest
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostCreateAPI {
    @POST("post/create")
    fun transferPostCreate(
        @Body request : PostCreateRequest
        ): Call<PostCreateResponse>
}