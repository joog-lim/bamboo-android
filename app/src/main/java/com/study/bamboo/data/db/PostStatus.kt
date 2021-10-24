package com.study.bamboo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostStatus(
    val sad: Boolean?,
    val good: Boolean?,

    @PrimaryKey(autoGenerate = false)
    var id: Int
)