package com.example.data.mapper

import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseResponse
import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.common.LoginEntity

fun BaseResponse.toDomain(): BaseEntity {
    return BaseEntity(
        this.status,
        this.message
    )
}
fun <T> BaseDataResponse<T>.toLoginDomain(): BaseDataEntity<LoginEntity> {
    return BaseDataEntity(this.status, this.message, this.data as LoginEntity)
}



