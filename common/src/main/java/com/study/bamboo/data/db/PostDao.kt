package com.study.bamboo.data.db

import androidx.room.*

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //데이터가 추가할때마다 수정
    suspend fun insertPost(postEntity: PostEntity)


    @Query("SELECT * FROM post_table ")
    suspend fun readPost(): List<PostEntity>

    @Delete
    suspend fun deletePost(postEntity: PostEntity)

    @Update
    suspend fun updatePost(postEntity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //데이터가 추가할때마다 수정
    suspend fun insertPostStatus(postStatus: PostStatus)


    @Query("SELECT * FROM post_table ")
    suspend fun readPostStatus(): List<PostStatus>

    @Delete
    suspend fun deletePostStatus(postStatus: PostStatus)

    @Update
    suspend fun updatePostStatus(postStatus: PostStatus)
}