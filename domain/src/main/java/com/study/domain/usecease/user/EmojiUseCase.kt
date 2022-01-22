package com.study.domain.usecease.user

import com.study.domain.base.BaseEntity
import com.study.domain.model.user.request.EmojiEntity
import com.study.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single

class EmojiUseCase(private val repository: UserRepository) {

    fun postEmoji(
        authorization: String,
        body: EmojiEntity
    ): Single<BaseEntity> {
        return repository.postEmoji(authorization, body)
    }

    fun deleteEmoji(
        authorization: String,
        body: EmojiEntity
    ): Single<BaseEntity> {
        return repository.deleteEmoji(authorization, body)
    }
}