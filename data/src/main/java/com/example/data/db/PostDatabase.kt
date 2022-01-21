package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.admin.response.Post


@Database(
    entities = [Post::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PostTypeConverter::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao


}