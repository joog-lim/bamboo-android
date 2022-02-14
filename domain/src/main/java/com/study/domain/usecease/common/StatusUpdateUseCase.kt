package com.study.domain.usecease.common

import com.study.domain.base.BaseEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.repository.CommonRepository
import io.reactivex.rxjava3.core.Single

class StatusUpdateUseCase(private val repository: CommonRepository) {
    fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusEntity
    ): Single<BaseEntity> {
        return repository.patchStatusAlgorithm(token, id, body)
    }
}