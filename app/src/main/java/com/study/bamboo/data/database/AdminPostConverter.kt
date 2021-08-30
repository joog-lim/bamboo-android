package com.study.bamboo.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.study.bamboo.utils.Admin

class AdminPostConverter {

    var gson = Gson()

    @TypeConverter
    fun adminAcceptToString(post: Admin.Accept): String {
        return gson.toJson(post)
    }

    @TypeConverter
    fun stringToAdminAccept(data: String): Admin.Accept {
        val listType = object : TypeToken<Admin.Accept>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun adminDeleteToString(post: Admin.Delete): String {
        return gson.toJson(post)
    }

    @TypeConverter
    fun stringToAdminDelete(data: String): Admin.Delete {
        val listType = object : TypeToken<Admin.Delete>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun adminPendingToString(post: Admin.Pending): String {
        return gson.toJson(post)
    }

    @TypeConverter
    fun stringToAdminPending(data: String): Admin.Pending {
        val listType = object : TypeToken<Admin.Pending>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun adminRejectToString(post: Admin.Reject): String {
        return gson.toJson(post)
    }

    @TypeConverter
    fun stringToAdminReject(data: String): Admin.Reject {
        val listType = object : TypeToken<Admin.Reject>() {}.type
        return gson.fromJson(data, listType)
    }

}