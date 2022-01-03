package com.example.data.model.user.postcreate

data class PostCreateRequest(
    val title: String,
    val content: String,
    val tag: String,
    val verifier: Verifier
)