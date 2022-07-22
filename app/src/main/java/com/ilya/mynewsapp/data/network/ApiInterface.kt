package com.ilya.mynewsapp.data.network

import com.ilya.mynewsapp.data.Repository
import com.ilya.mynewsapp.data.model.NewsResponse
import com.ilya.mynewsapp.utils.Constance
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(Constance.DESTINATION_URL)
     suspend fun getApi(
        @Query("apiKey")
        apiKey:String,
        @Query("country")
        countryCode: String
    ): Response<NewsResponse>

    @GET(Constance.DESTINATION_URL)
     suspend fun searchApi(
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey:String,
        @Query("country")
        countryCode: String
    ): Response<NewsResponse>
}