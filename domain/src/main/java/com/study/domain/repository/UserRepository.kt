package com.study.domain.repository

import androidx.paging.PagingData
import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.common.algorithm.DataEntity
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.EmojiEntity
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.response.*
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun algorithmCreate(request: AlgorithmCreate): Single<BaseDataEntity<AlgorithmCreateEntity>>
    fun getVerify(): Single<BaseDataEntity<GetVerifyEntity>>
    fun patchAlgorithmReport(
        id: String,
        body: Report,
    ): Single<BaseDataEntity<ReportEntity>>

    fun postEmoji(
        authorization: String,
        body: EmojiEntity
    ): Single<BaseEntity>

    fun deleteEmoji(
        authorization: String,
        body: EmojiEntity
    ): Single<BaseEntity>

    fun getAlgorithmPagingSource(
        token: String,

        ): Flow<PagingData<ResultEntity>>
}