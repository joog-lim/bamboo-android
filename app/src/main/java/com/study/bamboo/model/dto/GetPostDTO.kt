package com.study.bamboo.model.dto

import com.google.gson.annotations.SerializedName

data class GetPostDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("status")
    val status: String
)