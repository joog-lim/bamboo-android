package com.study.data.model.common.algorithm

import androidx.room.Entity


@Entity(tableName = "algorithm_table")
data class Result(
    val algorithmNumber: Int,
    val content: String,
    val createdAt: String,
    val emojiCount: Int,
    val emojis: List<Emoji>,
    val idx: Int,
    val isClicked: Boolean,
    val reason: String?,
    val tag: String,
    val title: String
)