package com.study.bamboo.data.paging.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.study.bamboo.data.network.user.AdminApi
import com.study.bamboo.data.paging.page.   AcceptPagingSource
import com.study.bamboo.data.paging.page.DeletePagingSource
import com.study.bamboo.data.paging.page.PendingPagingSource
import com.study.bamboo.data.paging.page.RejectPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PagingPostViewModel @Inject constructor(
    private val adminApi: AdminApi,

) : ViewModel() {

    val token = MutableLiveData<String>()
    val cursor = MutableLiveData<String>()

    fun getData(token: String, cursor: String) {
        this.token.value = token
        this.cursor.value = cursor
    }
    val acceptData = Pager(

        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        AcceptPagingSource(
            adminApi,
            token.value.toString(),

            )

    }.flow
        .cachedIn(viewModelScope)


    val pendingData = Pager(

        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        PendingPagingSource(
            adminApi,
            token.value.toString(),
            cursor.value.toString(),
        )
    }.flow
        .cachedIn(viewModelScope)


    val rejectData = Pager(

        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        RejectPagingSource(
            adminApi,
            token.value.toString(),
            cursor.value.toString(),
        )
    }.flow
        .cachedIn(viewModelScope)

    val deleteData = Pager(

        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
        )
    ) {
        DeletePagingSource(
            adminApi,
            token.value.toString(),
            cursor.value.toString(),
        )
    }.flow
        .cachedIn(viewModelScope)




}