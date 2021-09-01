package com.study.bamboo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//class SampleRepository @Inject constructor(
//
//
//){
//    suspend fun getNextPage(lastSeenId: Int): Int{
//        return withContext(Dispatchers.IO) {
//            (  lastSeenId.plus(PAGE_SIZE))
//        }
//    }
//
//    companion object {
//        const val PAGE_SIZE = 20
//    }
//}