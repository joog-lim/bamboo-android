package com.example.data.db

import androidx.room.*
import com.example.data.model.admin.response.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //데이터가 추가할때마다 수정
    suspend fun insertPost(postEntity: Post)


    @Query("SELECT * FROM algorithm_table ")
    suspend fun readPost(): List<Post>

    @Delete
    suspend fun deletePost(postEntity: Post)

    @Update
    suspend fun updatePost(postEntity: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //데이터가 추가할때마다 수정
    suspend fun insertPostStatus(postStatus: Post)


    @Query("SELECT * FROM algorithm_table ")
    suspend fun readPostStatus(): List<Post>

    @Delete
    suspend fun deletePostStatus(postStatus: Post)

    @Update
    suspend fun updatePostStatus(postStatus: Post)
}