package com.study.bamboo.data.network.models.user

data class UserPostDTO(
    val content: String,
    val createdAt: Long,
    val id: String,
    val number: Int,
    val status: String,
    val tag: String,
    val title: String
)