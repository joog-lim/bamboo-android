package com.study.data.repository.admin

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.study.data.mapper.toDomain
import com.study.data.mapper.user.toData
import com.study.data.model.common.algorithm.Data
import com.study.data.model.common.algorithm.Result
import com.study.data.network.admin.AdminApi
import com.study.data.paging.AlgorithmAdminPagingSource
import com.study.domain.base.BaseEntity
import com.study.domain.model.admin.request.AlgorithmModifyEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.model.common.algorithm.DataEntity
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.repository.AdminRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val dataSource: AdminDataSource,
    private val api: AdminApi
) : AdminRepository {
    override fun getAlgorithmPagingSource(
        token: String,
        status: String
    ): Flow<PagingData<ResultEntity>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { AlgorithmAdminPagingSource(api, token, status) }
        ).flow.map { it ->
            it.map { it.toDomain() }
        }
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


}





