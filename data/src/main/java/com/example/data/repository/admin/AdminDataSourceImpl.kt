package com.example.data.repository.admin

import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseDataSource
import com.example.data.base.BaseResponse
import com.example.data.model.admin.request.AlgorithmModifyRequest
import com.example.data.model.admin.request.SetStatusRequest
import com.example.data.model.admin.response.AlgorithmResponse
import com.example.data.network.admin.AdminApi
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class AdminDataSourceImpl @Inject constructor(override val service: AdminApi) : AdminDataSource,
    BaseDataSource<AdminApi>() {
    override fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusRequest
    ): Single<BaseResponse> {
        return service.patchStatusAlgorithm(token, id, body)
    }

    override fun patchModifyAlgorithm(
        token: String,
        id: String,
        body: AlgorithmModifyRequest
    ): Single<BaseResponse> {
        return service.patchModifyAlgorithm(token, id, body)
    }

    override fun deleteAlgorithm(token: String, id: String): Single<BaseResponse> {
        return service.deleteAlgorithm(token, id)
    }

    override fun getAlgorithmPage(
        token: String,
        count: Int,
        page: Int,
        status: String
    ): Single<BaseDataResponse<AlgorithmResponse>> {
        return service.getAlgorithmPage(token, count, page, status)
    }
}