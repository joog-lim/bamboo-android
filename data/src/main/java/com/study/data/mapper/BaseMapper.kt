package com.study.data.mapper

import com.study.data.base.BaseDataResponse
import com.study.data.base.BaseResponse
import com.study.data.model.common.LoginResponse
import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.common.LoginEntity

fun BaseResponse.toDomain(): BaseEntity {
    return BaseEntity(
        this.success,
        this.code,
        this.message
    )
}

fun  BaseDataResponse<LoginResponse>.toLoginDomain(): BaseDataEntity<LoginEntity> {
    return BaseDataEntity(this. success, this.code, this.message, this.data?.toDomain())
}

fun LoginResponse.toDomain():LoginEntity{
    return LoginEntity(
        this.accessToken,
        this.refreshToken,
        this.isAdmin
    )
}



