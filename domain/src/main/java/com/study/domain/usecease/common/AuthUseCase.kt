package com.study.domain.usecease.common

import com.study.domain.base.BaseDataEntity
import com.study.domain.base.BaseEntity
import com.study.domain.model.common.LoginEntity
import com.study.domain.repository.CommonRepository
import io.reactivex.rxjava3.core.Single

class AuthUseCase(private val repository:CommonRepository)  {

    fun postLogin(authorization: String): Single<BaseDataEntity<LoginEntity>>{
        return repository.postLogin(authorization)
    }
    fun deleteLogOut(authorization: String): Single<BaseEntity>{
        return repository.deleteLogOut(authorization)
    }
}