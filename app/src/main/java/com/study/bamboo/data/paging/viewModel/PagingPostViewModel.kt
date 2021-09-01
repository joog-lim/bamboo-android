package com.study.bamboo.data.paging.viewModel

import androidx.lifecycle.*
import androidx.paging.*
import com.study.bamboo.data.network.user.AdminApi
import com.study.bamboo.data.paging.page.AcceptPagingSource
import com.study.bamboo.data.paging.page.AcceptPagingSource.Companion.UNSPLASH_STARTING_PAGE_INDEX
import com.study.bamboo.data.paging.page.DeletePagingSource
import com.study.bamboo.data.paging.page.PendingPagingSource
import com.study.bamboo.data.paging.page.RejectPagingSource
import com.study.bamboo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


@HiltViewModel
class PagingPostViewModel @Inject constructor(
    private val repository: Repository,
    private val adminApi: AdminApi,
//    private val sampleRepository: SampleRepository
) : ViewModel() {

    private val viewModelJob = Job()
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


//    private val _pagingDataViewStates =
//        Pager(PagingConfig(pageSize = PAGE_SIZE)) {
//            AcceptPagingSource(
//                adminApi,
//               "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYWRtaW4iLCJpYXQiOjE2MzAzOTMyOTcsImV4cCI6MTYzMDQwNDA5N30.cbjzPQpJtFTPBDPAANNb-TC-1lKC76atTcHbTNUN5IM",
//               "",
//                sampleRepository
//            )
//        }.flow
//            .cachedIn(viewModelScope)
//            .asLiveData()
//            .let { it as MutableLiveData<PagingData<Admin.Accept>> }
//
//    val pagingDataViewStates: LiveData<PagingData<Admin.Accept>> = _pagingDataViewStates


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


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

//    fun onViewEvent(sampleViewEvents: SampleViewEvents) {
//        val paingData = pagingDataViewStates.value ?: return
//
//        when (sampleViewEvents) {
//            is SampleViewEvents.Remove -> {
//                paingData
//                    .filter { sampleViewEvents.sampleEntity.id != it.id }
//                    .let { _pagingDataViewStates.value = it }
//            }
//            is SampleViewEvents.Edit -> {
//                paingData
//                    .map {
//                        if (sampleViewEvents.sampleEntity.id == it.id)
//                            return@map it.copy(content = "${it.content} ${it.createdAt} ${it.id} ${it.number} ${it.status} ${it.tag} ${it.title}")
//                        else return@map it
//                    }
//                    .let { _pagingDataViewStates.value = it }
//            }
//
//            else -> null
//        }
//    }
}