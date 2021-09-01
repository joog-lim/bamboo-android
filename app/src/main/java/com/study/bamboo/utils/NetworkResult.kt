package com.study.bamboo.utils

//sealed class NetworkResult<T>(
//    val data: T? = null,
//    val message: String? = null
//) {
//    // 네트워크 상태에 따라 class 를 호출한다.
//    class Success<T>(data: T) : NetworkResult<T>(data)
//    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
//    class Loading<T> : NetworkResult<T>()
//}