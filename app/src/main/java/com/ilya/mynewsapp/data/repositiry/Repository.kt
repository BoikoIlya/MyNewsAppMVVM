package com.ilya.mynewsapp.data.repositiry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.data.model.NewsResponse
import com.ilya.mynewsapp.utils.Constance
import retrofit2.Response

interface Repository {

    suspend fun getApi(apiKey:String = Constance.API_KEY,
                       countryCode: String = "us",
                       pageSize:Int = 10,
                       page:Int = 1
    ):Response<NewsResponse>

    suspend fun searchApi(searchQuery: String,
                          apiKey:String = Constance.API_KEY,
                          countryCode: String = "us"
    ):Response<NewsResponse>

    suspend fun saveOrUpdateDataBase(article: Article)

     fun readFromDataBase(): LiveData<List<Article>>

    suspend fun deleteFromDataBase(article: Article)
}