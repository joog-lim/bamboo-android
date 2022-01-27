package com.study.data.repository.common

import android.util.Log
import com.study.data.base.BaseDataResponse
import com.study.data.mapper.toDomain
import com.study.data.mapper.toLoginDomain
import com.study.data.model.common.LoginResponse
import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.common.LoginEntity
import com.study.domain.model.user.TokenEntity
import com.study.domain.repository.CommonRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val dataSource: CommonDataSourceImpl,
) : CommonRepository {


    override suspend fun saveToken(token: String) {
        dataSource.saveToken(token)
    }

    override var readToken: Flow<TokenEntity>
        get() = dataSource.readToken.map { it.toDomain() }
        set(value) {
            dataSource.readToken = value.map { it.toDomain() }
        }


    override fun postLogin(token: String): Single<BaseDataEntity<LoginEntity>> {
        return dataSource.postLogin(token).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())?.map {
                Log.d("CommonRepositoryImpl", "postLogin: ${it}")
                it.toLoginDomain()
            } as Single<BaseDataEntity<LoginEntity>>

    }

    override fun deleteLogOut(authorization: String): Single<BaseEntity> {
        return dataSource.deleteLogOut(authorization).map { it.toDomain() }
    }

}

