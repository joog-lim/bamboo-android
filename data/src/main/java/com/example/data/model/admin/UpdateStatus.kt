package com.example.data.model.admin

data class UpdateStatus(
    val afterStatus: String,
    val beforeStatus: String,
    val content: String,
    val tag: String,
    val title: String
)