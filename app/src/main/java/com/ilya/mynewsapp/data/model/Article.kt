package com.ilya.mynewsapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilya.mynewsapp.utils.Constance
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Entity(
    tableName = Constance.TABLE_NAME
)
//@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val author: String?=null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
):Serializable