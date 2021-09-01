package com.study.bamboo.utils

import com.study.bamboo.data.network.models.user.UserPostDTOBase

sealed class Admin {

    data class Accept(
        override var content: String,
        override var createdAt: Long,
        override var id: String,
        override var number: Int,
        override var status: String,
        override var tag: String,
        override var title: String
    ) : UserPostDTOBase

    data class Delete(
        override val content: String,
        override val createdAt: Long,
        override val id: String,
        override val number: Int,
        override var status: String,
        override val tag: String,
        override val title: String
    ) : UserPostDTOBase

    data class Reject(
        override val content: String,
        override val createdAt: Long,
        override val id: String,
        override val number: Int,
        override var status: String,
        override val tag: String,
        override val title: String
    ) : UserPostDTOBase

    data class Pending(
        override val content: String,
        override val createdAt: Long,
        override val id: String,
        override val number: Int,
        override var status: String,
        override val tag: String,
        override val title: String
    ) : UserPostDTOBase
}