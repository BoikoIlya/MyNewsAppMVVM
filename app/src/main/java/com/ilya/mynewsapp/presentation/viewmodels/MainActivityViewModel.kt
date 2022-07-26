package com.ilya.mynewsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.ilya.mynewsapp.data.repositiry.Repository
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.data.model.NewsResponse
import com.ilya.mynewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    private val _breakingNewsApi = MutableLiveData<Resource<NewsResponse>>()
    val breakingNewsApi:LiveData<Resource<NewsResponse>>  = _breakingNewsApi
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    private val _searchNewsApi = MutableLiveData<Resource<NewsResponse>>()
    val searchNewsApi:LiveData<Resource<NewsResponse>>  = _searchNewsApi

     var newsDataBase:LiveData<List<Article>>? = null



    init {
       getCheckedNewsFromApi()
       getNewsFromDataBase()
    }

     fun getCheckedNewsFromApi(){
        _breakingNewsApi.value = Resource.Loading()
        viewModelScope.launch() {
            val response = try{
                repository.getApi(page = breakingNewsPage)
            }catch (e: IOException) {
                _breakingNewsApi.postValue(Resource.Error("Connection Failed"))
                return@launch
            }catch (e: HttpException){
                _breakingNewsApi.postValue(Resource.Error(e.message.toString()+"\nCode: "+e.code()))
                return@launch
            }
            if (response.isSuccessful){
                response.body()?.let { resultResponse->

                breakingNewsPage++
                if (breakingNewsResponse==null){
                    breakingNewsResponse = resultResponse
                }else {
                    val oldArticles = breakingNewsResponse
                    val newArticles = resultResponse
                    oldArticles?.articles?.addAll(newArticles.articles)
                    breakingNewsResponse = oldArticles

                }
                    _breakingNewsApi.value  = breakingNewsResponse?.let {Resource.Success(it)}
                }
            }else _breakingNewsApi.value = Resource.Error(response.message())
        }

    }

    suspend fun searchCheckedNewsFromApi(searchTitle: String){
        _searchNewsApi.value = Resource.Loading()
            val response = try{
                repository.searchApi(searchTitle)
            }catch (e: IOException) {
                _searchNewsApi.postValue(Resource.Error("Connection Failed"))
                return
            }catch (e: HttpException){
                _searchNewsApi.postValue(Resource.Error(e.message.toString()+"\nCode: "+e.code()))
                return
            }
            if (response.isSuccessful && response.body()!= null){
                _searchNewsApi.value = response.body()?.let { Resource.Success(it)}
            }else _breakingNewsApi.value = Resource.Error(response.message())
    }

     fun getNewsFromDataBase() {
         newsDataBase = repository.readFromDataBase()
    }

    fun saveToDataBase(article: Article) = viewModelScope.launch(Dispatchers.IO) {
            repository.saveOrUpdateDataBase(article)
    }

    fun deleteFromDataBase(article: Article) =viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFromDataBase(article)
    }
}



