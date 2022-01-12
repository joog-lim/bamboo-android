package com.example.app.di

import com.example.data.network.admin.AdminApi
import com.example.data.network.common.CommonApi
import com.example.data.network.user.UserApi
import com.example.data.repository.admin.AdminDataSourceImpl
import com.example.data.repository.common.CommonDataSourceImpl
import com.example.data.repository.user.UserDataSourceImpl
import com.example.data.utils.DataStoreManager
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