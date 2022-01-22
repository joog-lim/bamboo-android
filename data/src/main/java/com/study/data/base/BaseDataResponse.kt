package com.study.data.base

data class BaseDataResponse<T>(val status: Int, val message: String, val data: T)
