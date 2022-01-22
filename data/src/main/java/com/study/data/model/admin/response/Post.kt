package com.study.data.model.admin.response


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "algorithm_table")
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