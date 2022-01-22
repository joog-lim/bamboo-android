package com.example.domain.usecease.user

import com.example.domain.base.BaseDataEntity
import com.example.domain.model.user.request.AlgorithmCreate
import com.example.domain.model.user.request.Report
import com.example.domain.model.user.response.AlgorithmCreateEntity
import com.example.domain.model.user.response.GetVerifyEntity
import com.example.domain.model.user.response.ReportEntity
import com.example.domain.repository.UserRepository
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