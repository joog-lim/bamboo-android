package com.example.presentation.di

import com.example.data.network.common.CommonApi
import com.example.data.repository.admin.AdminDataSourceImpl
import com.example.data.repository.admin.AdminRepositoryImpl
import com.example.data.repository.common.CommonDataSourceImpl
import com.example.data.repository.common.CommonRepositoryImpl
import com.example.data.repository.user.UserDataSourceImpl
import com.example.data.repository.user.UserRepositoryImpl
import com.example.data.utils.DataStoreManager
import com.example.domain.repository.AdminRepository
import com.example.domain.repository.CommonRepository
import com.example.domain.repository.UserRepository
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
    fun providesAdminRepository(adminDataSource: AdminDataSourceImpl) : AdminRepository =
        AdminRepositoryImpl(adminDataSource)

    @Provides
    @Singleton
    fun providesUserRepository(dataSource: UserDataSourceImpl) : UserRepository =
        UserRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun providesCommonRepository(dataSource: CommonDataSourceImpl, api: CommonApi) :   CommonRepository =
        CommonRepositoryImpl(dataSource, api)
}