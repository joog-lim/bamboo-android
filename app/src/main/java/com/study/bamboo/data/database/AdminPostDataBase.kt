package com.study.bamboo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.study.bamboo.data.database.entity.*
//
//@Database(
//    entities = [AcceptEntity::class, RejectEntity::class, PendingEntity::class, DeleteEntity::class, RemoteKeys::class],
//    // 스키마 변경할때마다 버전을 올려야한다.
//    version = 1,
//    exportSchema = false
//)
//
////@TypeConverters(AdminPostConverter::class)
//abstract class AdminPostDataBase : RoomDatabase() {
//    abstract fun adminDao(): AdminDao
//    abstract fun remoteKeysDao(): RemoteKeysDao
//}