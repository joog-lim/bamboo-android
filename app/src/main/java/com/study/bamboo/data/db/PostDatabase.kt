package com.study.bamboo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities =[PostEntity::class,PostStatus::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PostTypeConverter::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao() : PostDao


}