package com.ilya.mynewsapp.data.database

import android.content.Context
import androidx.room.*
import com.ilya.mynewsapp.data.Repository
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.utils.Constance

@Database(
   version = 1,
    entities = [Article::class],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class NewsDataBase:RoomDatabase() {

    abstract fun newsDAO():NewsDAO

    companion object {
        @Volatile
        private var instance: NewsDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDataBase(context).also { instance=it }
        }

        fun createDataBase(context: Context):NewsDataBase {
           return Room.databaseBuilder(
                context.applicationContext,
                NewsDataBase::class.java,
                Constance.TABLE_NAME
            ).build()
        }
    }
}