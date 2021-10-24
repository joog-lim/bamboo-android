package com.study.bamboo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.study.bamboo.data.network.models.user.emoji.GetEmoji
import com.study.bamboo.util.Util.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class PostEntity(
    var getIEmoji: GetEmoji,


    ) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}