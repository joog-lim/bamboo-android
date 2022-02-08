package com.study.data.network.admin

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.AlgorithmModifyRequest
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.common.algorithm.Data
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface AdminApi {




    // 게시물 수정
    // id는 게시물 고유 index 값
    @PATCH("algorithm/{id}")
    fun patchModifyAlgorithm(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body body: AlgorithmModifyRequest,
    ): Single<BaseResponse>

    //게시물 삭제
    @HTTP(method = "DELETE", path = "algorithm/{id}", hasBody = true)
    fun deleteAlgorithm(
        @Header("Authorization") authorization: String,
        @Path("id") arg: String,
    ): Single<BaseResponse>


    @GET("algorithm/list/page/admin")
    suspend  fun getAlgorithmPage(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int,
        @Query("criteria") criteria: Int,
        @Query("status") status: String
    ): BaseDataResponse<Data>

}