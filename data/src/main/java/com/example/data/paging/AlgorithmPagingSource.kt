package com.example.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.admin.response.AlgorithmResponse
import com.example.data.model.admin.response.Post
import com.example.data.network.common.CommonApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AlgorithmPagingSource @Inject constructor(
    private val adminApi: CommonApi,
    private val token: String,
    private val status: String,


    ) : PagingSource<Int, Post>() {
    companion object {
        const val TAG = "PostPagingSource"

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 1
            val response = adminApi.getAlgorithmPage(token, 20, page, status)


            val responseData = mutableListOf<Post>()
            val data = response.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.data.posts?.let { it1 -> responseData.addAll(it1) }
                }, {
                    throw it
                })


            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (data.isDisposed) null else page.plus(1),
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


    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        Log.d(TAG, "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }




}


