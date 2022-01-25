package com.study.data.repository.admin

import androidx.paging.PagingSource
import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.AlgorithmModifyRequest
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.common.algorithm.Data
import com.study.data.model.common.algorithm.Result
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
    fun getAlgorithmPagingSource(
        token: String,
        status: String,

        ): PagingSource<Int, Result>


}