package com.study.data.network.common

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.common.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface CommonApi {

    //게시물 상태 수정 (수락 상태, 거절 상태 등)
    @PATCH("algorithm/{id}/status")
    fun patchStatusAlgorithm(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body body: SetStatusRequest,
    ): Single<BaseResponse>


    @POST("login")
    fun postLogin(
        @Header("Authorization") Authorization: String,
    ): Single<BaseDataResponse<LoginResponse>>
    @DELETE("logout")
    fun deleteLogOut(
        @Header("Authorization") authorization: String
    ): Single<BaseResponse>



}