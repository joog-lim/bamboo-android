package com.example.domain.usecease.user

import com.example.domain.base.BaseEntity
import com.example.domain.model.user.request.EmojiEntity
import com.example.domain.repository.UserRepository
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