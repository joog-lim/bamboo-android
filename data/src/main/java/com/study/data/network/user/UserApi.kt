package com.study.data.network.user


import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.data.model.common.algorithm.Data
import com.study.data.model.user.response.GetVerifyResponse
import com.study.data.model.user.response.AlgorithmCreateResponse
import com.study.data.model.user.request.AlgorithmCreateRequest
import com.study.data.model.user.request.EmojiRequest
import com.study.data.model.user.request.ReportRequest
import com.study.data.model.user.response.ReportResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UserApi {


    // 게시물 생성
    @POST("algorithm/")
    fun algorithmCreate(
        @Body request: AlgorithmCreateRequest
    ): Single<BaseDataResponse<AlgorithmCreateResponse>>


    @GET("verify")
    fun getVerify(): Single<BaseDataResponse<GetVerifyResponse>>


    @PATCH("{id}/report")
    fun patchAlgorithmReport(
        @Path("id") id: String,
        @Body body: ReportRequest,
    ): Single<BaseDataResponse<ReportResponse>>

    // 이모지
    @POST("leaf")
    fun postEmoji(
        @Header("Authorization") authorization: String,
        @Body body: EmojiRequest
    ): Single<BaseResponse>
    @HTTP(method = "DELETE", path = "leaf", hasBody = true)
    fun deleteEmoji(
        @Header("Authorization") authorization: String,
        @Body body: EmojiRequest
    ): Single<BaseResponse>

    @GET("algorithm/list/page")
    suspend  fun getAlgorithmPage(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int,
        @Query("criteria") criteria: Int,
    ): BaseDataResponse<Data>

}