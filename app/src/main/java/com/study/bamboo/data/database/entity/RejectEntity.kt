package com.study.bamboo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.study.bamboo.utils.Admin
import com.study.bamboo.utils.Util.Companion.Reject_Table

@Entity(tableName = Reject_Table)
class RejectEntity(
    var adminReject: Admin.Reject

) {
    @PrimaryKey(autoGenerate = false) // 기본키 중복 x
    var id: Int = 0
}