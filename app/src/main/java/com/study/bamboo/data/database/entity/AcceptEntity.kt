package com.study.bamboo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.study.bamboo.utils.Admin
import com.study.bamboo.utils.Util.Companion.Accept_Table

@Entity(tableName = Accept_Table)
class AcceptEntity(
    var adminAccept: Admin.Accept

) {
    @PrimaryKey(autoGenerate = false) // 기본키 중복 x
    var id: Int = 0
}
