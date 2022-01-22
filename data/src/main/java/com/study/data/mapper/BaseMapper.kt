package com.study.data.mapper

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.common.LoginEntity

fun BaseResponse.toDomain(): BaseEntity {
    return BaseEntity(
        this.status,
        this.message
    )
}
fun <T> BaseDataResponse<T>.toLoginDomain(): BaseDataEntity<LoginEntity> {
    return BaseDataEntity(this.status, this.message, this.data as LoginEntity)
}



