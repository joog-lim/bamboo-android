package com.study.bamboo.view.fragment.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.study.bamboo.data.database.entity.AcceptEntity
import com.study.bamboo.data.database.entity.DeleteEntity
import com.study.bamboo.data.database.entity.PendingEntity
import com.study.bamboo.data.database.entity.RejectEntity
import com.study.bamboo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataBaseViewModel @Inject constructor(
    private val repository: Repository

) : ViewModel() {

    fun insertAccept(post: AcceptEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertAccept(post)
        }
    }

    fun insertPending(post: PendingEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertPending(post)
        }
    }

    fun insertDelete(post: DeleteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertDelete(post)
        }
    }

    fun insertReject(post: RejectEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertReject(post)
        }
    }


}
