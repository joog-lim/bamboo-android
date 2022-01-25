package com.study.domain.base

data class BaseDataEntity<T>(val success:Boolean, val code: String, val message: String?, val data: T?)
