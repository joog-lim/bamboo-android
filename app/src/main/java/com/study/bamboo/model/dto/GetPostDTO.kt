package com.study.bamboo.model.dto

data class GetPostDTO(
    val count: Int,
    val cursor: String,
    val hasNext: Boolean,
    val posts: List<PostDTO>
)