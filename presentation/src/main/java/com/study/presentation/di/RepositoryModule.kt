package com.study.presentation.di

import com.study.data.network.common.CommonApi
import com.study.data.repository.admin.AdminDataSourceImpl
import com.study.data.repository.admin.AdminRepositoryImpl
import com.study.data.repository.common.CommonDataSourceImpl
import com.study.data.repository.common.CommonRepositoryImpl
import com.study.data.repository.user.UserDataSourceImpl
import com.study.data.repository.user.UserRepositoryImpl
import com.study.domain.repository.AdminRepository
import com.study.domain.repository.CommonRepository
import com.study.domain.repository.UserRepository
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