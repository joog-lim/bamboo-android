package com.study.data.db

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import  com.study.data.model.common.algorithm.Result
class PostTypeConverter {
    var gson = Gson()


    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }


    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }
}