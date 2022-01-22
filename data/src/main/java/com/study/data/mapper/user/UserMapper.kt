package com.study.data.mapper.user

import com.study.data.base.BaseDataResponse
import com.study.data.model.admin.request.AlgorithmModifyRequest
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.admin.response.AlgorithmResponse
import com.study.data.model.admin.response.Post
import com.study.data.model.user.request.AlgorithmCreateRequest
import com.study.data.model.user.request.EmojiRequest
import com.study.data.model.user.request.ReportRequest
import com.study.data.model.user.request.Verifier
import com.study.domain.base.BaseDataEntity
import com.study.domain.model.admin.request.AlgorithmModifyEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.model.admin.response.AlgorithmEntity
import com.study.domain.model.admin.response.PostEntity
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
        this.status
    )
}

fun AlgorithmModifyEntity.toData(): AlgorithmModifyRequest {
    return AlgorithmModifyRequest(
        this.title, this.content,
        this.tag
    )
}

fun AlgorithmResponse.toDomain(): AlgorithmEntity {
    return AlgorithmEntity(this.posts?.toDomain(), this.totalPage)

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


fun List<Post>.toDomain(): List<PostEntity> {

    return this.map {
        PostEntity(
            it.content,
            it.createdAt,
            it.id,
            it.number,
            it.status,
            it.tag,
            it.title

        )
    }
}

fun <T> BaseDataResponse<T>.toDomain(): BaseDataEntity<AlgorithmCreateEntity> {
    return BaseDataEntity(this.status, this.message, this.data as AlgorithmCreateEntity)
}
fun <T> BaseDataResponse<T>.toVerifyDomain(): BaseDataEntity<GetVerifyEntity> {
    return BaseDataEntity(this.status, this.message, this.data as GetVerifyEntity)
}
fun <T> BaseDataResponse<T>.toReportDomain(): BaseDataEntity<ReportEntity> {
    return BaseDataEntity(this.status, this.message, this.data as ReportEntity)
}
fun <T> BaseDataResponse<T>.toSignDomain(): BaseDataEntity<LoginEntity> {
    return BaseDataEntity(this.status, this.message, this.data as LoginEntity)
}