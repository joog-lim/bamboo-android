package com.study.data.repository.admin

import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.AlgorithmModifyRequest
import com.study.data.model.admin.request.SetStatusRequest
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


}