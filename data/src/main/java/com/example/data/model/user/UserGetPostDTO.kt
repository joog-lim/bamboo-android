package com.example.data.model.user

data class UserGetPostDTO(
    val count: Int,
    val cursor: String,
    val hasNext: Boolean,
    val posts: List<UserPostDTO>
)