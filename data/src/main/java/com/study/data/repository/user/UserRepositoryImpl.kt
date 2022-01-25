package com.study.data.repository.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.study.data.mapper.toDomain
import com.study.data.mapper.user.toData
import com.study.data.mapper.user.toDomain
import com.study.data.mapper.user.toReportDomain
import com.study.data.mapper.user.toVerifyDomain
import com.study.data.network.user.UserApi
import com.study.data.paging.AlgorithmUserPagingSource
import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.EmojiEntity
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.response.AlgorithmCreateEntity
import com.study.domain.model.user.response.GetVerifyEntity
import com.study.domain.model.user.response.ReportEntity
import com.study.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataSource: UserDataSource,private val api:UserApi) :
    UserRepository {

    override  fun algorithmCreate(request: AlgorithmCreate): Single<BaseDataEntity<AlgorithmCreateEntity>> {
        return dataSource.algorithmCreate(request.toData()).map { it.toDomain() }
    }

    override  fun getVerify(): Single<BaseDataEntity<GetVerifyEntity>> {
        return dataSource.getVerify().map { it.toVerifyDomain() }
    }

    override  fun patchAlgorithmReport(
        id: String,
        body: Report
    ): Single<BaseDataEntity<ReportEntity>> {
        return dataSource.patchReport(id, body.toData()).map { it.toReportDomain() }
    }




    override  fun postEmoji(authorization: String, body: EmojiEntity): Single<BaseEntity> {
        return dataSource.postEmoji(authorization, body.toData()).map { it.toDomain() }
    }

    override  fun deleteEmoji(authorization: String, body: EmojiEntity): Single<BaseEntity> {
        return dataSource.deleteEmoji(authorization, body.toData()).map { it.toDomain() }
    }
    override fun getAlgorithmPagingSource(
        token: String,
    ): Flow<PagingData<ResultEntity>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { AlgorithmUserPagingSource(api, token) }
        ).flow.map { it ->
            it.map { it.toDomain() }
        }
    }
}

