package com.study.data.repository.common

import androidx.paging.PagingSource
import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseDataSource
import com.study.data.base.BaseResponse
import com.study.data.model.admin.response.Post
import com.study.data.model.common.LoginResponse
import com.study.data.network.common.CommonApi
import com.study.data.paging.AlgorithmPagingSource
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

    override fun getAlgorithmPagingSource(
        token: String,
        status: String
    ): PagingSource<Int, Post> {
        return AlgorithmPagingSource(service,token, status)
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
}