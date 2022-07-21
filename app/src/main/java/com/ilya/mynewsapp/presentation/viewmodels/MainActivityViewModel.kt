package com.ilya.mynewsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.ilya.mynewsapp.data.Repository
import com.ilya.mynewsapp.data.network.RetrofitInstance
import com.ilya.mynewsapp.model.Article
import com.ilya.mynewsapp.model.NewsResponse
import com.ilya.mynewsapp.utils.Constance
import com.ilya.mynewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    private val _newsApi = MutableLiveData<Resource<NewsResponse>>()
    val newsApi:LiveData<Resource<NewsResponse>>  = _newsApi

    private val _newsDataBase = MutableLiveData<List<Article>>()
    val newsDataBase = _newsDataBase

    init {
       getCheckedNewsFromApi()
    }

    private fun getCheckedNewsFromApi(){
        _newsApi.value = Resource.Loading()
        viewModelScope.launch {
            val response = try{
                repository.getApi()
            }catch (e: IOException) {
                Log.d("tag", "Api: Connection failed")
                _newsApi.postValue(Resource.Error(e.message.toString()))
                return@launch
            }catch (e: HttpException){
                Log.d("tag", "Api: Unexpected response")
                _newsApi.postValue(Resource.Error(e.message.toString()))
                return@launch
            }
            if (response.isSuccessful && response.body()!= null){
                _newsApi.value = response.body()?.let { Resource.Success(it)}
            }
        }
    }

}