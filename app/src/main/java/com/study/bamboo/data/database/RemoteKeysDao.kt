//package com.study.bamboo.data.database
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.study.bamboo.data.database.entity.RemoteKeys
//
//@Dao
//interface RemoteKeysDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(remoteKey: List<RemoteKeys>)
//
//    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
//    suspend fun remoteKeysRepoId(repoId: Int): RemoteKeys?
//
//    @Query("DELETE FROM remote_keys")
//    suspend fun clearRemoteKeys()
//}