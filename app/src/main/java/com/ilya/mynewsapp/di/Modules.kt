package com.ilya.mynewsapp.di

import android.content.Context
import com.ilya.mynewsapp.data.Repository
import com.ilya.mynewsapp.data.RepositoryImplementation
import com.ilya.mynewsapp.data.database.NewsDAO
import com.ilya.mynewsapp.data.database.NewsDataBase
import com.ilya.mynewsapp.data.network.ApiInterface
import com.ilya.mynewsapp.data.network.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class Modules {

    @Provides
    fun provideRetrofitInstance(): ApiInterface
     {
     return RetrofitInstance.API
     }

    @Provides
    fun provideNewsDataBaseInstance(@ApplicationContext context: Context): NewsDAO
    {
        return NewsDataBase.createDataBase(context).newsDAO()
    }

    @Provides
    fun provideRepositoryImplementation(apiInstance:ApiInterface,
                                        dataBaseInstance: NewsDAO):Repository{
        return RepositoryImplementation(apiInstance, dataBaseInstance)
    }
}