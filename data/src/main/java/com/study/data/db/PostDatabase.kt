package com.study.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PostTypeConverter::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao


}