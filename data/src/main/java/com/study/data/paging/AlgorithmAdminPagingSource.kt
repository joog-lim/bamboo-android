package com.study.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.study.data.model.common.algorithm.Result
import com.study.data.network.admin.AdminApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AlgorithmAdminPagingSource @Inject constructor(
    private val adminApi: AdminApi,
    private val token: String,
    private val status: String,


    ) : PagingSource<Int, Result>() {
    companion object {
        const val TAG = "AlgorithmAdminPagingSource"

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1

            val response = adminApi.getAlgorithmPage(token, 20, page, status)

            Log.d(TAG, "load: ${response}")

            val data = response.data?.result
            val responseData = mutableListOf<Result>()
            data?.let { responseData.addAll(it) }

            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (responseData.isEmpty()) null else page.plus(1),
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


    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        Log.d(TAG, "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}


