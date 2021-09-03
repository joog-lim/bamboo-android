package com.study.bamboo.data.paging.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.data.network.user.AdminApi
import com.study.bamboo.utils.Admin
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RejectPagingSource @Inject constructor(
    private val adminApi: AdminApi,
    private val token: String,
    private val cursor: String?,
) : PagingSource<Int, Admin.Reject>() {



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Admin.Reject> {
        val TAG="RejectPagingSource"
        return try {
            val page = params.key ?: 0
            Log.d(TAG, "page : $page")


            val response = adminApi.getRejectPost(token, page, cursor, REJECTED)

            val data = response.body()?.posts ?: emptyList()


            Log.d(TAG, "count: ${response.body()!!.count}")
            Log.d(TAG, "nextPage : ${response.body()!!.hasNext}")
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = page.plus(20)
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

    override fun getRefreshKey(state: PagingState<Int, Admin.Reject>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

}


