package com.study.data.repository.common

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseDataSource
import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.common.LoginResponse
import com.study.data.network.common.CommonApi
import com.study.data.utils.DataStoreManager
import com.study.data.utils.Token
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonDataSourceImpl @Inject constructor(
    override val service: CommonApi,
    private val manager: DataStoreManager

) : CommonDataSource,
    BaseDataSource<CommonApi>() {

    override var readToken: Flow<Token>
        get() = manager.readToken
        set(value) {
            manager.readToken = value
        }


    override fun postLogin(token: String): Single<BaseDataResponse<LoginResponse>> {
        return service.postLogin(token)
    }

    override  fun deleteLogOut(authorization: String): Single<BaseResponse> {
        return service.deleteLogOut(authorization)
    }


    override suspend fun saveToken(token: String) {
        manager.saveToken(token)
    }
    override fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusRequest
    ): Single<BaseResponse> {
        return service.patchStatusAlgorithm(token, id, body)
    }
}