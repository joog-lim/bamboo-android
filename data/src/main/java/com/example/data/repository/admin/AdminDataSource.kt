package com.example.data.repository.admin

import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseResponse
import com.example.data.model.admin.request.AlgorithmModifyRequest
import com.example.data.model.admin.request.SetStatusRequest
import com.example.data.model.admin.response.AlgorithmResponse
import io.reactivex.rxjava3.core.Single

interface AdminDataSource {

    fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusRequest
    ): Single<BaseResponse>

    fun patchModifyAlgorithm(
        token: String,
        id: String,
        body: AlgorithmModifyRequest
    ): Single<BaseResponse>

    fun deleteAlgorithm(token: String, id: String): Single<BaseResponse>

    fun getAlgorithmPage(
        token: String,
        count: Int,
        page: Int,
        status: String
    ): Single<BaseDataResponse<AlgorithmResponse>>
}