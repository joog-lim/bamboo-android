package com.study.domain.usecease.admin

import com.study.domain.base.BaseEntity
import com.study.domain.model.admin.request.AlgorithmModifyEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.repository.AdminRepository
import io.reactivex.rxjava3.core.Single

class AlgorithmAdminUseCase(private val repository: AdminRepository) {

    fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusEntity
    ): Single<BaseEntity> {
        return repository.patchStatusAlgorithm(token, id, body)
    }

    fun patchModifyAlgorithm(
        token: String,
        id: String,
        body: AlgorithmModifyEntity
    ): Single<BaseEntity> {
        return repository.patchModifyAlgorithm(token, id, body)
    }

    fun deleteAlgorithm(
        token: String, id: String
    ): Single<BaseEntity> {
        return repository.deleteAlgorithm(token, id)
    }

}