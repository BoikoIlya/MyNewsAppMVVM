package com.ilya.mynewsapp.data

import com.ilya.mynewsapp.model.Article
import com.ilya.mynewsapp.model.NewsResponse
import com.ilya.mynewsapp.utils.Constance
import retrofit2.Response

interface Repository {

    suspend fun getApi(apiKey:String):Response<NewsResponse>

    suspend fun searchApi(apiKey:String, title: String):Response<NewsResponse>

    suspend fun saveOrUpdateDataBase(name: String)

    suspend fun readFromDataBase():List<Article>

    suspend fun deleteFromDataBase(id: Int)
}