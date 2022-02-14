package com.study.data.model.common

data class LoginResponse(
    val accessToken: String, val refreshToken: String, val isAdmin: Boolean
)
