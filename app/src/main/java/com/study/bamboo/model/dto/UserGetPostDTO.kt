package com.study.bamboo.model.dto

data class UserGetPostDTO(
    val count: Int,
    val cursor: String,
    val hasNext: Boolean,
    val posts: List<UserPostDTO>
)