package com.example.domain.base

data class BaseDataEntity<T>(val status: Int, val message: String, val data: T)
