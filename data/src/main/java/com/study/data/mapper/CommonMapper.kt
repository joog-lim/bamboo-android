package com.study.data.mapper

import com.study.data.model.admin.response.Post
import com.study.data.utils.Token
import com.study.domain.model.admin.response.PostEntity
import com.study.domain.model.user.TokenEntity

fun Token.toDomain(): TokenEntity {
    return TokenEntity(
        this.token
    )
}

fun TokenEntity.toDomain(): Token {
    return Token(
        this.token
    )
}

fun Post.toDomain(): PostEntity {
    return PostEntity(
        this.content,
        this.createdAt,
        this.id,
        this.number,
        this.status,
        this.tag,
        this.title
    )
}