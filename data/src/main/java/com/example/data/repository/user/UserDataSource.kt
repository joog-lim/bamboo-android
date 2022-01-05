package com.example.data.repository.user

import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseResponse
import com.example.data.model.user.request.AlgorithmCreateRequest
import com.example.data.model.user.request.EmojiRequest
import com.example.data.model.user.request.ReportRequest
import com.example.data.model.user.response.GetVerifyResponse
import com.example.data.model.user.response.ReportResponse
import com.example.data.model.user.response.SignResponse
import com.example.data.model.user.response.AlgorithmCreateResponse
import io.reactivex.rxjava3.core.Single


interface UserDataSource {
    suspend fun algorithmCreate(request: AlgorithmCreateRequest): Single<BaseDataResponse<AlgorithmCreateResponse>>
    suspend fun getVerify(): Single<BaseDataResponse<GetVerifyResponse>>
    suspend fun patchReport(
        id: String,
        body: ReportRequest,
    ): Single<BaseDataResponse<ReportResponse>>

    suspend fun postLogin(authorization: String): Single<BaseDataResponse<SignResponse>>
    suspend fun deleteLogOut(authorization: String): Single<BaseResponse>
    suspend fun postEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse>

    suspend fun deleteEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse>
}