package com.study.bamboo.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

// activity나 fragment가 제거되면 제거된다.
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: AdminRepository,
    localDataSource: AdminLocalRepository,
    dataStore: DataStoreRepository
) {
    val remote = remoteDataSource
    val local = localDataSource
    val dataStore = dataStore
}