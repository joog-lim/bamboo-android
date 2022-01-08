package com.study.bamboo.di

import com.example.data.repository.admin.AdminDataSourceImpl
import com.example.data.repository.admin.AdminRepositoryImpl
import com.example.data.repository.user.UserDataSourceImpl
import com.example.data.repository.user.UserRepositoryImpl
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
}