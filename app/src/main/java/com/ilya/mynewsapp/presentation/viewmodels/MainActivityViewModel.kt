package com.ilya.mynewsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.ilya.mynewsapp.data.Repository
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.data.model.NewsResponse
import com.ilya.mynewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    private val _breakingNewsApi = MutableLiveData<Resource<NewsResponse>>()
    val breakingNewsApi:LiveData<Resource<NewsResponse>>  = _breakingNewsApi

    private val _searchNewsApi = MutableLiveData<Resource<NewsResponse>>()
    val searchNewsApi:LiveData<Resource<NewsResponse>>  = _searchNewsApi

     var newsDataBase:LiveData<List<Article>>? = null


    init {
       getCheckedNewsFromApi()
       getNewsFromDataBase()
    }

    private fun getCheckedNewsFromApi(){
        _breakingNewsApi.value = Resource.Loading()
        viewModelScope.launch() {
            val response = try{
                repository.getApi()
            }catch (e: IOException) {
                Log.d("tag", "Api: Connection failed")
                _breakingNewsApi.postValue(Resource.Error(e.message.toString()))
                return@launch
            }catch (e: HttpException){
                Log.d("tag", "Api: Unexpected response")
                _breakingNewsApi.postValue(Resource.Error(e.message.toString()))
                return@launch
            }
            if (response.isSuccessful && response.body()!= null){
                _breakingNewsApi.value = response.body()?.let { Resource.Success(it)}
            }
        }
    }

    suspend fun searchCheckedNewsFromApi(searchTitle: String){
        _searchNewsApi.value = Resource.Loading()
            val response = try{
                repository.searchApi(searchTitle)
            }catch (e: IOException) {
                Log.d("tag", "Api: Connection failed")
                _searchNewsApi.postValue(Resource.Error(e.message.toString()))
                return
            }catch (e: HttpException){
                Log.d("tag", "Api: Unexpected response")
                _searchNewsApi.postValue(Resource.Error(e.message.toString()))
                return
            }
            if (response.isSuccessful && response.body()!= null){
                _searchNewsApi.value = response.body()?.let { Resource.Success(it)}
            }
    }

     fun getNewsFromDataBase() {
         newsDataBase = repository.readFromDataBase()
    }

    fun saveToDataBase(article: Article) = viewModelScope.launch(Dispatchers.IO) {
            repository.saveOrUpdateDataBase(article) }
    }

