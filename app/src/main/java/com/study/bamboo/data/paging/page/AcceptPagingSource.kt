package com.study.bamboo.data.paging.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTED
import com.study.bamboo.data.network.user.AdminApi
import com.study.bamboo.utils.Admin
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AcceptPagingSource @Inject constructor(
    private val adminApi: AdminApi,
    private val token: String,
    private val cursor: String?,
//    private val sampleRepository: SampleRepository,


) : PagingSource<Int, Admin.Accept>() {
    companion object {
        const val TAG = "PostPagingSource"
        const val UNSPLASH_STARTING_PAGE_INDEX = 20

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Admin.Accept> {
        return try {
//            val page = sampleRepository.getNextPage(lastSeenId = params.key ?: 0)
            val page = params.key ?: 0


//            val totalCount = adminApi.getCount(token)

//            val countData=totalCount.body()!![3].count
//            Log.d(TAG, "totalCount accept: $countData ")

            Log.d(TAG, "load: $page")

            val response = adminApi.getAcceptPost(token, page, cursor, ACCEPTED)

            Log.d(TAG, "load sortBy: ${ response.body()?.posts?.sortedBy { it.number }}")



            val data = response.body()?.posts ?: emptyList()
            data.sortedBy { it.number }

            LoadResult.Page(
                data = data.sortedByDescending { it.number },
                prevKey = null,
                nextKey = page.plus(20)
            )


        } catch (e: Exception) {
            Log.d(TAG, "error: $e")
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException: $e")
            return LoadResult.Error(e)
        } catch (e: IOException) {
            Log.d(TAG, "IOException: $e")
            return LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, Admin.Accept>): Int? {
        Log.d(TAG, "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(20)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(20)
        }

    }

}


