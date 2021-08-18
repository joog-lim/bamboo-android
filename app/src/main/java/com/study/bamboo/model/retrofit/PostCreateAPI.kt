package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.AdminSignInDTO
import com.study.bamboo.model.dto.postcreate.PostCreateRequest
import com.study.bamboo.model.dto.postcreate.PostCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostCreateAPI {
    @POST("post/create")
    fun transferPostCreate(
        @Body request : PostCreateRequest
        ): Call<PostCreateResponse>
}