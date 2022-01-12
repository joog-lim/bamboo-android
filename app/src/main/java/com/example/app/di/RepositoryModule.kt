package com.example.app.di

import com.example.data.network.common.CommonApi
import com.example.data.repository.admin.AdminDataSourceImpl
import com.example.data.repository.admin.AdminRepositoryImpl
import com.example.data.repository.common.CommonDataSourceImpl
import com.example.data.repository.common.CommonRepositoryImpl
import com.example.data.repository.user.UserDataSourceImpl
import com.example.data.repository.user.UserRepositoryImpl
import com.example.data.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesAdminRepository(adminDataSource: AdminDataSourceImpl) =
        AdminRepositoryImpl(adminDataSource)

    @Provides
    @Singleton
    fun providesUserRepository(dataSource: UserDataSourceImpl) =
        UserRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun providesCommonRepository(dataSource: CommonDataSourceImpl, api: CommonApi) =
        CommonRepositoryImpl(dataSource, api)
}