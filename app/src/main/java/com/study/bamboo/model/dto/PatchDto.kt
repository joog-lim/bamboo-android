package com.study.bamboo.model.dto

data class PatchDto(
    val __v: Int,
    val _id: String,
    val content: String,
    val createdAt: Long,
    val cursorId: String,
    val id: String,
    val number: Int,
    val reason: String,
    val status: String,
    val tag: String,
    val title: String,
    val updatedAt: String
)