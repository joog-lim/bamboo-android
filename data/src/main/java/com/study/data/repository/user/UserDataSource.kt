package com.study.data.repository.user

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.data.model.user.request.AlgorithmCreateRequest
import com.study.data.model.user.request.EmojiRequest
import com.study.data.model.user.request.ReportRequest
import com.study.data.model.user.response.GetVerifyResponse
import com.study.data.model.user.response.ReportResponse
import com.study.data.model.user.response.AlgorithmCreateResponse
import io.reactivex.rxjava3.core.Single


interface UserDataSource {
     fun algorithmCreate(request: AlgorithmCreateRequest): Single<BaseDataResponse<AlgorithmCreateResponse>>
     fun getVerify(): Single<BaseDataResponse<GetVerifyResponse>>
     fun patchReport(
        id: String,
        body: ReportRequest,
    ): Single<BaseDataResponse<ReportResponse>>

     fun postEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse>

     fun deleteEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse>
}