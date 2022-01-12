package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.admin.response.PostEntity
import com.example.domain.model.user.TokenEntity
import kotlinx.coroutines.flow.Flow

interface CommonRepository {


    suspend fun saveToken(token: String)
    var readToken: Flow<TokenEntity>

    fun getAlgorithmPagingSource(
        token: String,
        status: String,

        ):Flow<PagingData<PostEntity>>

}

