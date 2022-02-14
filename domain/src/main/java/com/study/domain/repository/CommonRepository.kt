package com.study.domain.repository

import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.domain.model.common.LoginEntity
import com.study.domain.model.user.TokenEntity
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface CommonRepository {


    suspend fun saveToken(token: String)
    var readToken: Flow<TokenEntity>



    fun postLogin(token: String): Single<BaseDataEntity<LoginEntity>>
    fun deleteLogOut(authorization: String): Single<BaseEntity>
    fun patchStatusAlgorithm(
        token: String,
        id: String,
        body: SetStatusEntity
    ): Single<BaseEntity>

}

