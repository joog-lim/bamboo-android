package com.study.bamboo.view.fragment.admin.paging.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.study.bamboo.data.network.user.AdminApi
import com.study.bamboo.view.fragment.admin.paging.AcceptPagingSource
import com.study.bamboo.view.fragment.admin.paging.AcceptPagingSource.Companion.UNSPLASH_STARTING_PAGE_INDEX
import com.study.bamboo.view.fragment.admin.paging.DeletePagingSource
import com.study.bamboo.view.fragment.admin.paging.PendingPagingSource
import com.study.bamboo.view.fragment.admin.paging.RejectPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PagingPostViewModel @Inject constructor(
    application: Application,
    private val adminApi: AdminApi
) : AndroidViewModel(application) {
    private val TAG = "PagingPostViewModel"

    val token = MutableLiveData<String>()
    val cursor = MutableLiveData<String>()


    fun getData(token: String, cursor: String, status: String) {
        this.token.value = token
        this.cursor.value = cursor
    }



    val acceptData = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = UNSPLASH_STARTING_PAGE_INDEX,
            enablePlaceholders = false
        )
    ) {
        AcceptPagingSource(
            adminApi,
            token.value.toString(),
            cursor.value.toString(),
        )
    }.flow
        .cachedIn(viewModelScope)

    val pendingData = Pager(

        PagingConfig(
            pageSize = UNSPLASH_STARTING_PAGE_INDEX,
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
            pageSize = UNSPLASH_STARTING_PAGE_INDEX,
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
            pageSize = UNSPLASH_STARTING_PAGE_INDEX,
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