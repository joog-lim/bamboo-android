package com.study.presentation.di

import com.study.data.network.admin.AdminApi
import com.study.data.network.common.CommonApi
import com.study.data.network.user.UserApi
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
    fun providesAdminRepository(adminDataSource: AdminDataSourceImpl,api:AdminApi) : AdminRepository =
        AdminRepositoryImpl(adminDataSource,api)

    @Provides
    @Singleton
    fun providesUserRepository(dataSource: UserDataSourceImpl,api:UserApi) : UserRepository =
        UserRepositoryImpl(dataSource,api)

    @Provides
    @Singleton
    fun providesCommonRepository(dataSource: CommonDataSourceImpl) :   CommonRepository =
        CommonRepositoryImpl(dataSource)
}