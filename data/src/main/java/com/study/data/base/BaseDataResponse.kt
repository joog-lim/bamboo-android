package com.study.data.base

data class BaseDataResponse<T>(val success:Boolean, val code: String, val message: String?, val `data`: T?)
