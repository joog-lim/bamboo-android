package com.example.data.network.common

import com.example.data.base.BaseDataResponse
import com.example.data.model.admin.response.AlgorithmResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CommonApi {

    // 게시물 페이지로 조히
    @GET("algorithm/page")
    fun getAlgorithmPage(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int,
        @Query("page") page: Int,
        @Query("status") status: String
    ): Flowable<BaseDataResponse<AlgorithmResponse>>

}