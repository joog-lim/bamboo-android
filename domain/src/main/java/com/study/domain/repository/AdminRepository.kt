package com.study.domain.repository

import androidx.paging.PagingData
import com.study.domain.base.BaseEntity
import com.study.domain.model.admin.request.AlgorithmModifyEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.model.common.algorithm.ResultEntity
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface AdminRepository {



    fun patchModifyAlgorithm(
        token: String,
        id: String,
        body: AlgorithmModifyEntity
    ): Single<BaseEntity>

    fun deleteAlgorithm(token: String, id: String): Single<BaseEntity>

    fun getAlgorithmPagingSource(
        token: String,
        status: String,

        ): Flow<PagingData<ResultEntity>>
}