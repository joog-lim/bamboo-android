package com.study.bamboo.data.network.models.user

data class UserGetPostDTO(
    val count: Int,
    val cursor: String,
    val hasNext: Boolean,
    val posts: List<UserPostDTO>
)