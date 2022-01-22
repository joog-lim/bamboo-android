package com.study.data.repository.user

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseDataSource
import com.study.data.base.BaseResponse
import com.study.data.model.user.request.AlgorithmCreateRequest
import com.study.data.model.user.request.EmojiRequest
import com.study.data.model.user.request.ReportRequest
import com.study.data.model.user.response.GetVerifyResponse
import com.study.data.model.user.response.ReportResponse
import com.study.data.model.user.response.AlgorithmCreateResponse
import com.study.data.network.user.UserApi
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