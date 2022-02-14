package com.study.data.db

import androidx.room.*
import com.study.data.model.common.algorithm.Result
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //데이터가 추가할때마다 수정
    suspend fun insertPost(postEntity: Result)


    @Query("SELECT * FROM algorithm_table ")
    suspend fun readPost(): List<Result>

    @Delete
    suspend fun deletePost(postEntity: Result)

    @Update
    suspend fun updatePost(postEntity: Result)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //데이터가 추가할때마다 수정
    suspend fun insertPostStatus(postStatus: Result)


    @Query("SELECT * FROM algorithm_table ")
    suspend fun readPostStatus(): List<Result>

    @Delete
    suspend fun deletePostStatus(postStatus: Result)

    @Update
    suspend fun updatePostStatus(postStatus: Result)
}