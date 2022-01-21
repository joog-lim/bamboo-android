package com.example.data.db

import androidx.room.TypeConverter
import com.example.data.model.admin.response.Post
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class PostTypeConverter {
    var gson = Gson()


    @TypeConverter
    fun stringToResult(data: String): Post {
        val listType = object : TypeToken<Post>() {}.type
        return gson.fromJson(data, listType)
    }


    @TypeConverter
    fun resultToString(result: Post): String {
        return gson.toJson(result)
    }
}