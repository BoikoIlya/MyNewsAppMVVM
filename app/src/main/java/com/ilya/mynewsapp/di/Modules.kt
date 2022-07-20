package com.ilya.mynewsapp.di

import com.ilya.mynewsapp.data.Repository
import com.ilya.mynewsapp.data.network.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class Modules {

    @Provides
    fun provideRetrofitInstance(): Repository
     {
     return RetrofitInstance.API
     }
}