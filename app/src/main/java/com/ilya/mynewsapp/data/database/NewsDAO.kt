package com.ilya.mynewsapp.data.database


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.utils.Constance

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun saveOrUpdateDataBase(article: Article)

    @Delete
     suspend fun deleteFromDataBase(article: Article)

    @Query("SELECT * FROM ${Constance.TABLE_NAME}")
      fun readFromDataBase():  LiveData<List<Article>>

}