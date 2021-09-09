package com.study.bamboo.data.paging.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.study.bamboo.data.network.user.AdminApi
import com.study.bamboo.utils.Admin
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PendingPagingSource @Inject constructor(
    private val adminApi: AdminApi,
    private val token: String,
    private val cursor: String?,

    ) : PagingSource<Int, Admin.Pending>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Admin.Pending> {
        val TAG = "PendingPagingSource"
        return try {
            val page = params.key ?: 1

            Log.d(TAG, "page : $page")


            val response = adminApi.getPendingPost(token, page, "PENDING")
//            val totalCount=adminApi.getCount(token)
//            val countData=totalCount.body()!![2].count
//            Log.d(AcceptPagingSource.TAG, "totalCount pending: $countData ")
            val data = response.body()?.posts ?: emptyList()


            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey =  if (data.isEmpty()) null else page.inc(),
            )


        } catch (e: Exception) {
            Log.d(TAG, "error: $e")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException: $e")
            LoadResult.Error(e)
        } catch (e: IOException) {
            Log.d(TAG, "IOException: $e")
            LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, Admin.Pending>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}


