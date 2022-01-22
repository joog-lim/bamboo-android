package com.study.presentation.di

import com.study.domain.repository.AdminRepository
import com.study.domain.repository.CommonRepository
import com.study.domain.repository.UserRepository
import com.study.domain.usecease.admin.AlgorithmAdminUseCase
import com.study.domain.usecease.common.GetAlgorithmUseCase
import com.study.domain.usecease.common.SaveTokenUseCase
import com.study.domain.usecease.user.AlgorithmUserUseCase
import com.study.domain.usecease.common.AuthUseCase
import com.study.domain.usecease.user.EmojiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideAlgorithmAdminUseCase(repository:AdminRepository) : AlgorithmAdminUseCase =  AlgorithmAdminUseCase(repository)
    @Provides
    @Singleton
    fun provideGetAlgorithmUseCase(repository: CommonRepository) : GetAlgorithmUseCase =  GetAlgorithmUseCase(repository)
    @Provides
    @Singleton
    fun provideSaveTokenUseCase(repository:CommonRepository) : SaveTokenUseCase =  SaveTokenUseCase(repository)
    @Provides
    @Singleton
    fun provideAlgorithmUserUseCase(repository:UserRepository) : AlgorithmUserUseCase =  AlgorithmUserUseCase(repository)
    @Provides
    @Singleton
    fun provideEmojiUserUseCase(repository:UserRepository) : EmojiUseCase =  EmojiUseCase(repository)
    @Provides
    @Singleton
    fun provideAuthUserUseCase(repository:CommonRepository) : AuthUseCase =  AuthUseCase(repository)
}
