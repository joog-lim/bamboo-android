package com.study.data.repository.user

import com.study.data.mapper.toDomain
import com.study.data.mapper.user.*
import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.EmojiEntity
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.response.AlgorithmCreateEntity
import com.study.domain.model.user.response.GetVerifyEntity
import com.study.domain.model.user.response.ReportEntity
import com.study.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataSource: UserDataSource) :
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
}

