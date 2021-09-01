//package com.study.bamboo.data.repository.local
//
//import androidx.paging.DataSource
//import androidx.paging.PagingSource
//import com.study.bamboo.data.database.AdminDao
//import com.study.bamboo.data.database.entity.DeleteEntity
//import com.study.bamboo.data.database.entity.PendingEntity
//import com.study.bamboo.data.database.entity.RejectEntity
//import javax.inject.Inject
//
//class AdminLocalRepository @Inject constructor(
//    private val adminPostDao: AdminDao
//) {
////
////    suspend fun insertAccept(adminAccept: AcceptEntity) {
////        return adminPostDao.insertAcceptPost(adminAccept)
////    }
////
//
////
////    fun getAllAccept(): DataSource.Factory<Int, AcceptEntity> {
////        return adminPostDao.getAllAccept()
////    }
//
//    fun getAllDelete(): DataSource.Factory<Int, DeleteEntity> {
//        return adminPostDao.getAllDelete()
//    }
//
//    fun getAllPending(): DataSource.Factory<Int, PendingEntity> {
//        return adminPostDao.getAllPending()
//    }
//
//    fun getAllReject(): DataSource.Factory<Int, RejectEntity> {
//        return adminPostDao.getAllReject()
//    }
////
////    suspend fun acceptClear() =
////        adminPostDao.acceptPostClear()
//    suspend fun deleteClear() =
//        adminPostDao.deletePostClear()
//    suspend fun rejectClear() =
//        adminPostDao.rejectPostClear()
//    suspend fun pendingClear() =
//        adminPostDao.pendingPostClear()
//
//
//}