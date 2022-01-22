package com.study.domain.model.common

data class LoginEntity(val accessToken: String, val refreshToken: String, val isAdmin: Boolean)
