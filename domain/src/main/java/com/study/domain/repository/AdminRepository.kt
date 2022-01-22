package com.study.domain.repository

import com.study.domain.base.BaseEntity
import com.study.domain.model.admin.request.AlgorithmModifyEntity
import com.study.domain.model.admin.request.SetStatusEntity
import io.reactivex.rxjava3.core.Single

interface AdminRepository {

    fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusEntity
    ): Single<BaseEntity>

    fun patchModifyAlgorithm(
        token: String,
        id: String,
        body: AlgorithmModifyEntity
    ): Single<BaseEntity>

    fun deleteAlgorithm(token: String, id: String): Single<BaseEntity>


}