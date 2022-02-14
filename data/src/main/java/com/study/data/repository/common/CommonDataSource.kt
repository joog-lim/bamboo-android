package com.study.data.repository.common

import androidx.paging.PagingSource
import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.data.model.admin.request.SetStatusRequest
import com.study.data.model.common.LoginResponse
import com.study.data.model.common.algorithm.Data
import com.study.data.utils.Token
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface CommonDataSource {

    // Token save

    suspend fun saveToken(token: String)

    var readToken: Flow<Token>


    fun postLogin(token: String): Single<BaseDataResponse<LoginResponse>>
    fun deleteLogOut(authorization: String): Single<BaseResponse>

    fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusRequest
    ): Single<BaseResponse>

}