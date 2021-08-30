package com.study.bamboo.view.fragment.admin.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.study.bamboo.data.retrofit.AdminApi
import com.study.bamboo.utils.Admin
import com.study.bamboo.view.fragment.admin.paging.AcceptPagingSource.Companion.UNSPLASH_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeletePagingSource @Inject constructor(
    private val adminApi: AdminApi,
    private val token: String,
    private val cursor: String?,

    ) : PagingSource<Int, Admin.Delete>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Admin.Delete> {
        val TAG = "DeletePagingSource"
        return try {
            val page = params.key ?: 0

            Log.d(TAG, "page : $page")


            val response = adminApi.getDeletePost(token, page, cursor, "DELETED")
            Log.d(TAG, "load: ${response.body()}")

            val data = response.body()!!.posts

            val totalCount = adminApi.getCount(token)

            val countData = totalCount.body()!![1].count
            Log.d(AcceptPagingSource.TAG, "totalCount delete: $countData ")
            Log.d(TAG, "nextPage : ${response.body()!!.hasNext}")
            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page.minus(20),
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

    override fun getRefreshKey(state: PagingState<Int, Admin.Delete>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

}


