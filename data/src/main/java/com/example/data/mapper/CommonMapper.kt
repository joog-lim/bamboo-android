package com.example.data.mapper

import com.example.data.model.admin.response.Post
import com.example.data.utils.Token
import com.example.domain.model.admin.response.PostEntity
import com.example.domain.model.user.TokenEntity

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