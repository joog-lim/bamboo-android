package com.study.domain.usecease.user

import com.study.domain.base.BaseDataEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.response.AlgorithmCreateEntity
import com.study.domain.model.user.response.GetVerifyEntity
import com.study.domain.model.user.response.ReportEntity
import com.study.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single

class AlgorithmUserUseCase(private val repository: UserRepository) {
    fun algorithmCreate(request: AlgorithmCreate): Single<BaseDataEntity<AlgorithmCreateEntity>> {
        return repository.algorithmCreate(request)
    }

    fun patchAlgorithmReport(
        id: String,
        body: Report,
    ): Single<BaseDataEntity<ReportEntity>> {
        return repository.patchAlgorithmReport(id, body)
    }

    fun getVerify(): Single<BaseDataEntity<GetVerifyEntity>> {
        return repository.getVerify()
    }
}