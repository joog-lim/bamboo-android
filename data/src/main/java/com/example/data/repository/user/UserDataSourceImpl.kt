package com.example.data.repository.user

import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseDataSource
import com.example.data.base.BaseResponse
import com.example.data.model.user.request.AlgorithmCreateRequest
import com.example.data.model.user.request.EmojiRequest
import com.example.data.model.user.request.ReportRequest
import com.example.data.model.user.response.GetVerifyResponse
import com.example.data.model.user.response.ReportResponse
import com.example.data.model.user.response.SignResponse
import com.example.data.model.user.response.AlgorithmCreateResponse
import com.example.data.network.user.UserApi
import io.reactivex.rxjava3.core.Single

class UserDataSourceImpl(override val service: UserApi) : UserDataSource,
    BaseDataSource<UserApi>() {
    override suspend fun algorithmCreate(request: AlgorithmCreateRequest): Single<BaseDataResponse<AlgorithmCreateResponse>> {
        return service.algorithmCreate(request)
    }

    override suspend fun getVerify(): Single<BaseDataResponse<GetVerifyResponse>> {

        return service.getVerify()
    }

    override suspend fun patchReport(
        id: String,
        body: ReportRequest
    ): Single<BaseDataResponse<ReportResponse>> {
        return service.patchReport(id, body)

    }

    override suspend fun postLogin(authorization: String): Single<BaseDataResponse<SignResponse>> {
        return service.postLogin(authorization)
    }

    override suspend fun deleteLogOut(authorization: String): Single<BaseResponse> {
        return service.deleteLogOut(authorization)
    }

    override suspend fun postEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse> {
        return service.postEmoji(authorization, body)
    }

    override suspend fun deleteEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse> {
        return service.deleteEmoji(authorization, body)
    }


}