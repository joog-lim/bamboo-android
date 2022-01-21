package com.example.data.repository.common

import androidx.paging.PagingSource
import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseResponse
import com.example.data.model.admin.response.AlgorithmResponse
import com.example.data.model.admin.response.Post
import com.example.data.model.common.LoginResponse
import com.example.data.network.common.CommonApi
import com.example.data.utils.Token
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface CommonDataSource {

    // Token save

    suspend fun saveToken(token: String)

    var readToken: Flow<Token>
    fun getAlgorithmPagingSource(
        token: String,
        status: String,

        ): PagingSource<Int, Post>

    fun postLogin(token: String): Single<BaseDataResponse<LoginResponse>>
    fun deleteLogOut(authorization: String): Single<BaseResponse>

}