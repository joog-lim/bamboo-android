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


    ) : PagingSource<Int, Admin.Accept>() {
    companion object {
        const val TAG = "PostPagingSource"

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Admin.Accept> {
        return try {
            val page = params.key ?: 1
            val response = adminApi.getAcceptPage("", page, ACCEPTED)


            val responseData = mutableListOf<Admin.Accept>()
            val data = response.body()?.posts ?: emptyList()

            responseData.addAll(data)

            Log.d(TAG, "dataSet ${response.body()?.posts?.map { it.number }}")


            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (data.isEmpty()) null else page.plus(1),
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
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}


