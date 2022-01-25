package com.study.domain.model.common.algorithm

import com.study.domain.model.common.algorithm.EmojiEntity


data class ResultEntity(
    val algorithmNumber: Int,
    val content: String,
    val createdAt: String,
    val emojiCount: Int,
    val emojiEntities: List<EmojiEntity>,
    val idx: Int,
    val isClicked: Boolean,
    val reason: String?,
    val tag: String,
    val title: String
)