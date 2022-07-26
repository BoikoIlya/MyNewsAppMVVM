package com.ilya.mynewsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilya.mynewsapp.data.Repository
import com.ilya.mynewsapp.data.model.Article
import retrofit2.HttpException
import java.io.IOException

class ArticlePagingSource(private val repository: Repository): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try{
            val pageNumber = params.key ?: 1
            val response = repository.getApi(page = pageNumber)

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.body()?.articles!!.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = response.body()!!.articles,
                prevKey = prevKey,
                nextKey = nextKey
            )

        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
    }
