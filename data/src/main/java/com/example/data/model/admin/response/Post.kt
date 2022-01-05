package com.example.data.model.admin.response


import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: Long?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tag")
    val tag: String?,
    @SerializedName("title")
    val title: String?
)