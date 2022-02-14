package com.study.data.repository.admin

import androidx.paging.PagingSource
import com.study.data.base.BaseDataSource
import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.AlgorithmModifyRequest
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.common.algorithm.Data
import com.study.data.model.common.algorithm.Result
import com.study.data.network.admin.AdminApi
import com.study.data.paging.AlgorithmAdminPagingSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AdminDataSourceImpl @Inject constructor(override val service: AdminApi) : AdminDataSource,
    BaseDataSource<AdminApi>() {

    override fun getAlgorithmPagingSource(
        token: String,
        status: String
    ): PagingSource<Int, Result> {
        return AlgorithmAdminPagingSource(service,token, status)
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