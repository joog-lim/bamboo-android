package com.example.data.mapper.user

import com.example.data.base.BaseDataResponse
import com.example.data.model.admin.request.AlgorithmModifyRequest
import com.example.data.model.admin.request.SetStatusRequest
import com.example.data.model.admin.response.AlgorithmResponse
import com.example.data.model.admin.response.Post
import com.example.data.model.user.request.AlgorithmCreateRequest
import com.example.data.model.user.request.EmojiRequest
import com.example.data.model.user.request.ReportRequest
import com.example.data.model.user.request.Verifier
import com.example.domain.base.BaseDataEntity
import com.example.domain.model.admin.request.AlgorithmModifyEntity
import com.example.domain.model.admin.request.SetStatusEntity
import com.example.domain.model.admin.response.AlgorithmEntity
import com.example.domain.model.admin.response.PostEntity
import com.example.domain.model.user.request.AlgorithmCreate
import com.example.domain.model.user.request.EmojiEntity
import com.example.domain.model.user.request.Report
import com.example.domain.model.user.request.VerifierEntity
import com.example.domain.model.user.response.AlgorithmCreateEntity
import com.example.domain.model.user.response.GetVerifyEntity
import com.example.domain.model.user.response.ReportEntity
import com.example.domain.model.user.response.SignEntity


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
fun <T> BaseDataResponse<T>.toSignDomain(): BaseDataEntity<SignEntity> {
    return BaseDataEntity(this.status, this.message, this.data as SignEntity)
}