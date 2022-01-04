package com.example.data.model.user.request

data class AlgorithmCreateRequest(
    val title: String,
    val content: String,
    val tag: String,
    val verifier: Verifier
)