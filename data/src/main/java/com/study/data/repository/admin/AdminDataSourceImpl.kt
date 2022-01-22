package com.study.data.repository.admin

import com.study.data.base.BaseDataSource
import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.AlgorithmModifyRequest
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.network.admin.AdminApi
import io.reactivex.rxjava3.core.Single
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


}