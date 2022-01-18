package com.example.domain.usecease.common

import com.example.domain.model.user.TokenEntity
import com.example.domain.repository.CommonRepository
import kotlinx.coroutines.flow.Flow

class SaveTokenUseCase(private val repository: CommonRepository) {

    suspend fun saveToken(token: String) {
        return repository.saveToken(token)
    }

    var readToken: Flow<TokenEntity>
        get() = repository.readToken
        set(value) {
            repository.readToken = value
        }
}