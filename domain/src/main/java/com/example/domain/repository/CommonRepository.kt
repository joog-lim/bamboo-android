package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.admin.response.PostEntity
import com.example.domain.model.common.LoginEntity
import com.example.domain.model.user.TokenEntity
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface CommonRepository {


    suspend fun saveToken(token: String)
    var readToken: Flow<TokenEntity>

    fun getAlgorithmPagingSource(
        token: String,
        status: String,

        ): Flow<PagingData<PostEntity>>

    fun postLogin(token: String): Single<BaseDataEntity<LoginEntity>>
    fun deleteLogOut(authorization: String): Single<BaseEntity>


}

