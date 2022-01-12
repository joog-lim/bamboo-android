package com.example.domain.usecease.user

import com.example.domain.base.BaseDataEntity
import com.example.domain.base.BaseEntity
import com.example.domain.model.user.response.SignEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single

class AuthUseCase(private val repository:UserRepository)  {

    fun postLogin(authorization: String): Single<BaseDataEntity<SignEntity>>{
        return repository.postLogin(authorization)
    }
    fun deleteLogOut(authorization: String): Single<BaseEntity>{
        return repository.deleteLogOut(authorization)
    }
}