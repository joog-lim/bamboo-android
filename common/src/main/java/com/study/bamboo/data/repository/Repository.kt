package com.study.bamboo.data.repository

import com.study.bamboo.data.repository.remote.AdminRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

// activity나 fragment가 제거되면 제거된다.
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: AdminRepository,
    dataStore: DataStoreRepository
) {
    val remote = remoteDataSource
    val dataStore = dataStore
}