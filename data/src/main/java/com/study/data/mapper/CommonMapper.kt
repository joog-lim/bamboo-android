package com.study.data.mapper

import com.study.data.model.common.algorithm.Data
import com.study.data.model.common.algorithm.Emoji
import com.study.data.utils.Token
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.model.user.TokenEntity
import com.study.data.model.common.algorithm.Result
import com.study.domain.model.common.algorithm.DataEntity
import com.study.domain.model.common.algorithm.EmojiEntity

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


fun Data.toDomain(): DataEntity = DataEntity(this.result.toDomain(), this.status)

fun List<Result>.toDomain(): List<ResultEntity> {

    return this.map {
        ResultEntity(
            it.algorithmNumber,
            it.content,
            it.createdAt,
            it.emojiCount,
            it.emojis.toDomain(),
            it.idx,
            it.isClicked,
            it.reason,
            it.tag,
            it.title,
        )
    }
}

@JvmName("toDomainEmoji")
fun List<Emoji>.toDomain(): List<EmojiEntity> {
    return this.map {
        EmojiEntity(it.idx)
    }
}


fun Result.toDomain(): ResultEntity {

    return ResultEntity(
        this.algorithmNumber,
        this.content,
        this.createdAt,
        this.emojiCount,
        this.emojis.toDomain(),
        this.idx,
        this.isClicked,
        this.reason,
        this.tag,
        this.title,
    )
}

