package com.example.presentation.di

import com.example.domain.repository.AdminRepository
import com.example.domain.repository.CommonRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecease.admin.AlgorithmAdminUseCase
import com.example.domain.usecease.common.GetAlgorithmUseCase
import com.example.domain.usecease.common.SaveTokenUseCase
import com.example.domain.usecease.user.AlgorithmUserUseCase
import com.example.domain.usecease.common.AuthUseCase
import com.example.domain.usecease.user.EmojiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
