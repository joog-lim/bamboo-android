package com.study.data.network.common

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.data.model.common.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface CommonApi {



    @POST("login")
    fun postLogin(
        @Header("Authorization") Authorization: String,
    ): Single<BaseDataResponse<LoginResponse>>
    @DELETE("logout")
    fun deleteLogOut(
        @Header("Authorization") authorization: String
    ): Single<BaseResponse>



}