package com.study.bamboo.model.dto.postcreate

data class PostCreateRequest(
    val title: String,
    val content: String,
    val tag: String,
    val verifier: Verifier
)