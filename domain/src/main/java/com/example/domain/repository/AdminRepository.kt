package com.example.domain.repository

import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.admin.request.AlgorithmModifyEntity
import com.example.domain.model.admin.request.SetStatusEntity
import com.example.domain.model.admin.response.AlgorithmEntity
import io.reactivex.rxjava3.core.Flowable
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

    fun getAlgorithmPage(
        token: String,
        count: Int,
        page: Int,
        status: String
    ): Flowable<BaseDataEntity<AlgorithmEntity>>
}