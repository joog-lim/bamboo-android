package com.example.data.repository.user

import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseDataSource
import com.example.data.base.BaseResponse
import com.example.data.model.user.request.AlgorithmCreateRequest
import com.example.data.model.user.request.EmojiRequest
import com.example.data.model.user.request.ReportRequest
import com.example.data.model.user.response.GetVerifyResponse
import com.example.data.model.user.response.ReportResponse
import com.example.data.model.common.LoginResponse
import com.example.data.model.user.response.AlgorithmCreateResponse
import com.example.data.network.user.UserApi
import io.reactivex.rxjava3.core.Single

class UserDataSourceImpl(override val service: UserApi) : UserDataSource,
    BaseDataSource<UserApi>() {
    override  fun algorithmCreate(request: AlgorithmCreateRequest): Single<BaseDataResponse<AlgorithmCreateResponse>> {
        return service.algorithmCreate(request)
    }

    override  fun getVerify(): Single<BaseDataResponse<GetVerifyResponse>> {

        return service.getVerify()
    }

    override  fun patchReport(
        id: String,
        body: ReportRequest
    ): Single<BaseDataResponse<ReportResponse>> {
        return service.patchAlgorithmReport(id, body)

    }



    override  fun postEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse> {
        return service.postEmoji(authorization, body)
    }

    override  fun deleteEmoji(
        authorization: String,
        body: EmojiRequest
    ): Single<BaseResponse> {
        return service.deleteEmoji(authorization, body)
    }


}