package com.study.bamboo.data.repository

import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.study.bamboo.data.database.AdminDao
import com.study.bamboo.data.database.entity.AcceptEntity
import com.study.bamboo.data.database.entity.DeleteEntity
import com.study.bamboo.data.database.entity.PendingEntity
import com.study.bamboo.data.database.entity.RejectEntity
import javax.inject.Inject

class AdminLocalRepository @Inject constructor(
    private val adminPostDao: AdminDao
) {

    suspend fun insertAccept(adminAccept: AcceptEntity) {
        return adminPostDao.insertAcceptPost(adminAccept)
    }

    suspend fun insertPending(adminPending: PendingEntity) {
        return adminPostDao.insertPendingPost(adminPending)
    }

    suspend fun insertReject(adminReject: RejectEntity) {
        return adminPostDao.insertRejectPost(adminReject)
    }

    suspend fun insertDelete(adminDelete: DeleteEntity) {
        return adminPostDao.insertDeletePost(adminDelete)
    }

    fun getAllAccept(): DataSource.Factory<Int, AcceptEntity> {
        return adminPostDao.getAllAccept()
    }

    fun getAllDelete(): PagingSource<Int, DeleteEntity> {
        return adminPostDao.getAllDelete()
    }

    fun getAllPending(): PagingSource<Int, PendingEntity> {
        return adminPostDao.getAllPending()
    }

    fun getAllReject(): PagingSource<Int, RejectEntity> {
        return adminPostDao.getAllReject()
    }


}