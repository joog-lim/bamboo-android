package com.example.domain.repository

import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.user.request.AlgorithmCreate
import com.example.domain.model.user.request.EmojiEntity
import com.example.domain.model.user.request.Report
import com.example.domain.model.user.response.AlgorithmCreateEntity
import com.example.domain.model.user.response.GetVerifyEntity
import com.example.domain.model.user.response.ReportEntity
import com.example.domain.model.user.response.SignEntity
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    suspend fun algorithmCreate(request: AlgorithmCreate): Single<BaseDataEntity<AlgorithmCreateEntity>>
    suspend fun getVerify(): Single<BaseDataEntity<GetVerifyEntity>>
    suspend fun patchReport(
        id: String,
        body: Report,
    ): Single<BaseDataEntity<ReportEntity>>

    suspend fun postLogin(authorization: String): Single<BaseDataEntity<SignEntity>>
    suspend fun deleteLogOut(authorization: String): Single<BaseEntity>
    suspend fun postEmoji(
        authorization: String,
        body: EmojiEntity
    ): Single<BaseEntity>

    suspend fun deleteEmoji(
        authorization: String,
        body: EmojiEntity
    ): Single<BaseEntity>
}