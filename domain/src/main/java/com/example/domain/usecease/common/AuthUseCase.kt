package com.example.domain.usecease.common

import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.common.LoginEntity
import com.example.domain.repository.CommonRepository
import io.reactivex.rxjava3.core.Single

class AuthUseCase(private val repository:CommonRepository)  {

    fun postLogin(authorization: String): Single<BaseDataEntity<LoginEntity>>{
        return repository.postLogin(authorization)
    }
    fun deleteLogOut(authorization: String): Single<BaseEntity>{
        return repository.deleteLogOut(authorization)
    }
}