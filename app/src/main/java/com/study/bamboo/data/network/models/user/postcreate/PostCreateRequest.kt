package com.study.bamboo.data.network.models.user.postcreate

data class PostCreateRequest(
    val title: String,
    val content: String,
    val tag: String,
    val verifier: Verifier
)