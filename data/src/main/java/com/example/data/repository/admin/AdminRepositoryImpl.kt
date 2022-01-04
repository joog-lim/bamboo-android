package com.example.data.repository.admin

import com.example.data.mapper.admin.toAlgorithmDomain
import com.example.data.mapper.toDomain
import com.example.data.mapper.user.toData
import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.admin.request.AlgorithmModifyEntity
import com.example.domain.model.admin.request.SetStatusEntity
import com.example.domain.model.admin.response.AlgorithmEntity
import com.example.domain.repository.AdminRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val dataSource: AdminDataSource
) : AdminRepository {
    override fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusEntity
    ): Single<BaseEntity> {
        return dataSource.patchStatusAlgorithm(token, id, body.toData()).map { it.toDomain() }
    }

    override fun patchModifyAlgorithm(
        token: String,
        id: String,
        body: AlgorithmModifyEntity
    ): Single<BaseEntity> {
        return dataSource.patchModifyAlgorithm(token, id, body.toData()).map { it.toDomain() }
    }

    override fun deleteAlgorithm(token: String, id: String): Single<BaseEntity> {
        return dataSource.deleteAlgorithm(token, id).map { it.toDomain() }
    }

    override fun getAlgorithmPage(
        token: String,
        count: Int,
        page: Int,
        status: String
    ): Flowable<BaseDataEntity<AlgorithmEntity>> {
        return dataSource.getAlgorithmPage(token, count, page, status)
            .map { it.toAlgorithmDomain() }
    }
}





