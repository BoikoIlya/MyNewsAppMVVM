package com.ilya.mynewsapp.data

import com.ilya.mynewsapp.model.Article
import com.ilya.mynewsapp.model.NewsResponse
import com.ilya.mynewsapp.utils.Constance
import retrofit2.Response

interface Repository {

    suspend fun getApi(apiKey:String = Constance.API_KEY,
                       countryCode: String = "us"
    ):Response<NewsResponse>

    suspend fun searchApi(searchQuery: String,
                          apiKey:String = Constance.API_KEY,
                          countryCode: String = "us"
    ):Response<NewsResponse>

    suspend fun saveOrUpdateDataBase(name: String)

    suspend fun readFromDataBase():List<Article>

    suspend fun deleteFromDataBase(id: Int)
}