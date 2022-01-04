package com.example.data.base

data class BaseDataResponse<T>(val status: Int, val message: String, val data: T)
