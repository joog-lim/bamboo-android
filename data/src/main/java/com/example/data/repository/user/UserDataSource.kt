package com.example.data.repository.user

import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseResponse
import com.example.data.model.user.request.AlgorithmCreateRequest
import com.example.data.model.user.request.EmojiRequest
import com.example.data.model.user.request.ReportRequest
import com.example.data.model.user.response.GetVerifyResponse
import com.example.data.model.user.response.ReportResponse
import com.example.data.model.user.response.AlgorithmCreateResponse
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