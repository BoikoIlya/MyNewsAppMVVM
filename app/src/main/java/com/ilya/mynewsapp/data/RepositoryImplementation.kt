package com.ilya.mynewsapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ilya.mynewsapp.data.database.NewsDAO
import com.ilya.mynewsapp.data.database.NewsDataBase
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.data.model.NewsResponse
import com.ilya.mynewsapp.data.network.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    val apiInstance:ApiInterface,
    val dataBaseInstance: NewsDAO
): Repository {

    override suspend fun getApi(apiKey: String, countryCode: String): Response<NewsResponse> {
       return apiInstance.getApi(apiKey,countryCode)
    }

    override suspend fun searchApi(
        searchQuery: String,
        apiKey: String,
        countryCode: String
    ): Response<NewsResponse> {
        return apiInstance.searchApi(searchQuery,
        apiKey,
        countryCode
        )
    }

    override suspend fun saveOrUpdateDataBase(article: Article) {
        dataBaseInstance.saveOrUpdateDataBase(article)
    }

    override  fun readFromDataBase():  LiveData<List<Article>> {
        return dataBaseInstance.readFromDataBase()
    }

    override suspend fun deleteFromDataBase(article: Article) {
        dataBaseInstance.deleteFromDataBase(article)
    }
}