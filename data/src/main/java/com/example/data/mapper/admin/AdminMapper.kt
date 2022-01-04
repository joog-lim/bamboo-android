package com.example.data.mapper.admin

import com.example.data.base.BaseDataResponse
import com.example.domain.base.BaseDataEntity
import com.example.domain.model.admin.response.AlgorithmEntity
import com.example.domain.model.user.response.SignEntity

fun <T> BaseDataResponse<T>.toAlgorithmDomain(): BaseDataEntity<AlgorithmEntity> {
    return BaseDataEntity(this.status, this.message, this.data as AlgorithmEntity)
}