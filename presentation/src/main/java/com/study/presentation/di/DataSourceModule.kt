package com.study.presentation.di

import com.study.data.network.admin.AdminApi
import com.study.data.network.common.CommonApi
import com.study.data.network.user.UserApi
import com.study.data.repository.admin.AdminDataSourceImpl
import com.study.data.repository.common.CommonDataSourceImpl
import com.study.data.repository.user.UserDataSourceImpl
import com.study.data.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providesAdminDataSource(service: AdminApi) =
        AdminDataSourceImpl(service)

    @Provides
    @Singleton
    fun providesUserDataSource(service: UserApi) =
        UserDataSourceImpl(service)

    @Provides
    @Singleton
    fun providesCommonDataSource(service: CommonApi,dataSource: DataStoreManager) =
        CommonDataSourceImpl(service,dataSource)
}