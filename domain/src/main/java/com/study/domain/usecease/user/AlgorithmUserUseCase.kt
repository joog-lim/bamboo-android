package com.study.domain.usecease.user

import androidx.paging.PagingData
import com.study.domain.base.BaseDataEntity
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.response.*
import com.study.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

class AlgorithmUserUseCase(private val repository: UserRepository) {
    fun getAlgorithmPagingSource(
        token: String,

        ): Flow<PagingData<ResultEntity>> {
        return repository.getAlgorithmPagingSource(token)
    }

    fun algorithmCreate(request: AlgorithmCreate): Single<BaseDataEntity<AlgorithmCreateEntity>> {
        return repository.algorithmCreate(request)
    }



    fun getVerify(): Single<BaseDataEntity<GetVerifyEntity>> {
        return repository.getVerify()
    }
}