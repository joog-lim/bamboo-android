package com.example.data.network.user


import com.example.data.model.user.response.GetVerifyResponse
import com.example.data.model.user.response.create.AlgorithmCreateResponse
import com.example.data.model.user.request.AlgorithmCreateRequest
import com.example.data.model.user.request.EmojiRequest
import com.example.data.model.user.request.ReportRequest
import com.example.data.model.user.response.ReportResponse
import com.example.data.model.user.response.SignResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {


    // 게시물 생성
    @POST("algorithm/")
    suspend fun algorithmCreate(
        @Body request: AlgorithmCreateRequest
    ): Response<AlgorithmCreateResponse>

    @GET("verify")
    suspend fun getVerify(): Response<GetVerifyResponse?>


    @PATCH("post/{id}/report")
    suspend fun report(
        @Path("id") id: String,
        @Body body: ReportRequest,
    ): Response<ReportResponse>


    @POST("login")
    suspend fun postLogin(
        @Header("Authorization") Authorization: String,
    ): Response<SignResponse>


    @DELETE("logout")
    suspend fun deleteLogOut(
        @Header("Authorization") authorization: String
    ): Response<Void>


    // 이모지
    suspend fun postLeaf(
        @Header("Authorization") authorization: String,
        @Body body: EmojiRequest
    ): Response<Void>

    suspend fun deleteLeaf(
        @Header("Authorization") authorization: String,
        @Body body: EmojiRequest
    ): Response<Void>


}