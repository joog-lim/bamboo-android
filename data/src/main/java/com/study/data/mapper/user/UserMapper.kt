package com.study.data.mapper.user

import com.study.data.base.BaseDataResponse
import com.study.data.model.admin.request.AlgorithmModifyRequest
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.user.request.AlgorithmCreateRequest
import com.study.data.model.user.request.EmojiRequest
import com.study.data.model.user.request.ReportRequest
import com.study.data.model.user.request.Verifier
import com.study.data.model.user.response.AlgorithmCreateResponse
import com.study.data.model.user.response.GetVerifyResponse
import com.study.data.model.user.response.ReportResponse
import com.study.domain.base.BaseDataEntity
import com.study.domain.model.admin.request.AlgorithmModifyEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.model.common.LoginEntity
import com.study.domain.model.user.request.AlgorithmCreate
import com.study.domain.model.user.request.EmojiEntity
import com.study.domain.model.user.request.Report
import com.study.domain.model.user.request.VerifierEntity
import com.study.domain.model.user.response.AlgorithmCreateEntity
import com.study.domain.model.user.response.GetVerifyEntity
import com.study.domain.model.user.response.ReportEntity


fun SetStatusEntity.toData(): SetStatusRequest {
    return SetStatusRequest(
        this.status, this.reason
    )
}

fun AlgorithmModifyEntity.toData(): AlgorithmModifyRequest {
    return AlgorithmModifyRequest(
        this.title, this.content,
    )
}


fun AlgorithmCreate.toData(): AlgorithmCreateRequest {
    return AlgorithmCreateRequest(
        this.title,
        this.content,
        this.tag,
        this.verifier.toData()
    )
}

fun VerifierEntity.toData(): Verifier {

    return Verifier(
        this.id,
        this.answer
    )
}

fun Report.toData(): ReportRequest {

    return ReportRequest(
        this.reason
    )
}

fun EmojiEntity.toData(): EmojiRequest {

    return EmojiRequest(
        this.num
    )
}


fun BaseDataResponse<AlgorithmCreateResponse>.toDomain(): BaseDataEntity<AlgorithmCreateEntity> {
    return BaseDataEntity(this.success, this.code, this.message, this.data?.toDomain())
}

fun AlgorithmCreateResponse.toDomain(): AlgorithmCreateEntity {
    return AlgorithmCreateEntity(
        this.id
    )
}

fun BaseDataResponse<GetVerifyResponse>.toVerifyDomain(): BaseDataEntity<GetVerifyEntity> {
    return BaseDataEntity(this.success, this.code, this.message, this.`data`?.toDomain())
}

fun GetVerifyResponse.toDomain(): GetVerifyEntity {
    return GetVerifyEntity(
        this.id,
        this.question
    )
}

