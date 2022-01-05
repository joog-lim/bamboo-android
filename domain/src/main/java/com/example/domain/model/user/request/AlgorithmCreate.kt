package com.example.domain.model.user.request


data class AlgorithmCreate(
    val title: String,
    val content: String,
    val tag: String,
    val verifier: VerifierEntity
)