package com.study.bamboo.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.study.bamboo.data.network.models.user.UserPostDTO
import com.study.bamboo.data.network.user.UserApi
import com.study.bamboo.view.activity.signin.SignInActivity
import javax.inject.Inject

class GetPostSource @Inject constructor(
    private val userApi: UserApi
) : PagingSource<Int, UserPostDTO>() {

    companion object {
        private var FIRST_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPostDTO> {

        return try {
            val page = params.key ?: FIRST_PAGE_INDEX

            val getPostResponse = userApi.getPost(page, "ACCEPTED")

            val data = getPostResponse.body()?.posts ?: emptyList()
            FIRST_PAGE_INDEX += 1

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if(data.isEmpty()) null else page+1
            )


        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserPostDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}