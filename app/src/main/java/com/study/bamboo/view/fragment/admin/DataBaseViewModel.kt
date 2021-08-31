//package com.study.bamboo.view.fragment.admin
//
//import android.app.Application
//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.NetworkCapabilities
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.lifecycle.*
//import androidx.paging.*
//import com.study.bamboo.data.database.entity.AcceptEntity
//import com.study.bamboo.data.network.models.admin.AcceptPost
//import com.study.bamboo.data.repository.Repository
//import com.study.bamboo.utils.Admin
//import com.study.bamboo.utils.NetworkResult
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//
//@HiltViewModel
//class DataBaseViewModel @Inject constructor(
//    private val repository: Repository,
//    application: Application,
//
//    ) : AndroidViewModel(application) {
//
//    fun insertAccept(post: AcceptEntity) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.local.insertAccept(post)
//        }
//    }
//
//
////    private val getAccept: LiveData<PagedList<Admin.Accept>>
//
//    init {
//        val pagingConfig = PagedList.Config.Builder()
//            .setInitialLoadSizeHint(10)
//            .setPageSize(10)
//            .setPrefetchDistance(5)
//            .build()
//
////        getAccept = Pager(
////            PagingConfig(
////                pagingConfig.pageSize,
////                pagingConfig.prefetchDistance,
////                pagingConfig.enablePlaceholders,
////                pagingConfig.initialLoadSizeHint,
////                pagingConfig.maxSize
////            ),
////
////            repository.local.getAllAccept().asPagingSourceFactory(Dispatchers.IO)
////        ).liveData
////            .build()
//    }
//
//    private val _setItem = MutableLiveData<DataSource<Int, Admin.Accept>>()
//    val setItem: MutableLiveData<DataSource<Int, Admin.Accept>> get() = _setItem
//
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun hasInternetConnection(): Boolean {
//        val connectivityManager = getApplication<Application>().getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        //연결 관리자
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//        return when {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }
//
//    suspend fun clearAccept() = repository.local.acceptClear()
//    suspend fun clearDelete() = repository.local.deleteClear()
//    suspend fun clearPending() = repository.local.pendingClear()
//    suspend fun clearReject() = repository.local.rejectClear()
//
//
//}
