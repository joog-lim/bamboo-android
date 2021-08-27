package com.study.bamboo.view.fragment.admin.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.study.bamboo.model.dto.UserPostDTO
import com.study.bamboo.model.retrofit.AdminApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val UNSPLASH_STARTING_PAGE_INDEX = 1

class PostPagingSource @Inject constructor(
    private val adminApi: AdminApi,
    private val token: String,
    private val cursor: String?,
    private val status: String
) : PagingSource<Int, UserPostDTO>() {
    companion object {
        const val TAG = "PostPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPostDTO> {
        return try {
            val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
            Log.d(TAG, "token: $token, cursor: $cursor, status :$status ")
            Log.d(TAG, "page : $page")
            val response = adminApi.getPost(token, page, cursor, status)
            val responseData = mutableListOf<UserPostDTO>()

            val data = response.body()?.posts ?: emptyList()
            responseData.addAll(data)


            Log.d(TAG, "count: ${ response.body()!!.count}")
            Log.d(TAG, "nextPage : ${response.body()!!.hasNext}")
            LoadResult.Page(
                data = responseData,
                prevKey = if (page == 1) null else page.minus(20),
                nextKey = if (page < response.body()!!.count)
                    response.body()!!.count.plus(20) else null
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

    override fun getRefreshKey(state: PagingState<Int, UserPostDTO>): Int? {
        Log.d(TAG, "getRefreshKey: ")
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }
}


