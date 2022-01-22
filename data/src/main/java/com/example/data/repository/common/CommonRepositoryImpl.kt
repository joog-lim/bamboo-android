package com.example.data.repository.common

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.base.BaseDataResponse
import com.example.data.mapper.toDomain
import com.example.data.mapper.toLoginDomain
import com.example.data.network.common.CommonApi
import com.example.data.paging.AlgorithmPagingSource
import io.reactivex.rxjava3.core.Single
import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.admin.response.PostEntity
import com.example.domain.model.common.LoginEntity
import com.example.domain.model.user.TokenEntity
import com.example.domain.repository.CommonRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val dataSource: CommonDataSourceImpl,
    private val api: CommonApi,
) : CommonRepository {


    override suspend fun saveToken(token: String) {
        dataSource.saveToken(token)
    }

    override var readToken: Flow<TokenEntity>
        get() = dataSource.readToken.map { it.toDomain() }
        set(value) {
            dataSource.readToken = value.map { it.toDomain() }
        }

    override fun getAlgorithmPagingSource(
        token: String,
        status: String
    ): Flow<PagingData<PostEntity>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { AlgorithmPagingSource(api, token, status) }
        ).flow.map { it ->
            it.map { it.toDomain() }
        }
    }

    override fun postLogin(token: String): Single<BaseDataEntity<LoginEntity>> {
        return dataSource.postLogin(token).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).map {
                it.toLoginDomain()
            }

    }

    override fun deleteLogOut(authorization: String): Single<BaseEntity> {
        return dataSource.deleteLogOut(authorization).map { it.toDomain() }
    }

}
