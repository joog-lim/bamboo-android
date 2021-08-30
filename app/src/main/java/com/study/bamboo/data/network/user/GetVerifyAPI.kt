package com.study.bamboo.data.retrofit

import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.UserGetPostDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVerifyAPI {
    @GET("verify")
    fun getVerify() : Call<GetVerifyDTO>
}