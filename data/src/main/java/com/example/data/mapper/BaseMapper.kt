package com.example.data.mapper

import com.example.data.base.BaseResponse
import com.example.domain.base.BaseEntity

fun BaseResponse.toDomain(): BaseEntity {
    return BaseEntity(
        this.status,
        this.message
    )
}


