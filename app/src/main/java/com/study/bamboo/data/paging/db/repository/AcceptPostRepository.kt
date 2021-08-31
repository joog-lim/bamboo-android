package com.study.bamboo.data.paging.db.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.study.bamboo.data.network.user.AdminApi

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class GithubRepository @Inject constructor(
//    private val adminApi: AdminApi,
//    private val database: AdminPostDataBase
//) {
//
//    /**
//     * Search repositories whose names match the query, exposed as a stream of data that will emit
//     * every time we get more data from the network.
//     */
//    @ExperimentalPagingApi
//    fun getSearchResultStream(token:String, cursor:String): Flow<PagingData<AcceptEntity>> {
//
//        val pagingSourceFactory = { database.adminDao().getAllAccept() }
//
//        return Pager(
//            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
//            initialKey="",
//            remoteMediator = GithubRemoteMediator(
//                token,
//                cursor,
//                adminApi,
//                database,
//            ),
//            pagingSourceFactory = pagingSourceFactory
//        ).flow
//    }
//
//
//    companion object {
//        const val NETWORK_PAGE_SIZE = 20
//    }
//}