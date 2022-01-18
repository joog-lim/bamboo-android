package com.example.data.repository.user

import com.example.data.mapper.toDomain
import com.example.data.mapper.user.*
import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.user.request.AlgorithmCreate
import com.example.domain.model.user.request.EmojiEntity
import com.example.domain.model.user.request.Report
import com.example.domain.model.user.response.AlgorithmCreateEntity
import com.example.domain.model.user.response.GetVerifyEntity
import com.example.domain.model.user.response.ReportEntity
import com.example.domain.repository.UserRepository
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

