package com.study.domain.usecease.common

import androidx.paging.PagingData
import com.study.domain.model.admin.response.PostEntity
import com.study.domain.repository.CommonRepository
import kotlinx.coroutines.flow.Flow

class GetAlgorithmUseCase(private val repository: CommonRepository) {

    fun getAlgorithmPagingSource(
        token: String,
        status: String,

        ): Flow<PagingData<PostEntity>> {
        return repository.getAlgorithmPagingSource(token, status)
    }

}