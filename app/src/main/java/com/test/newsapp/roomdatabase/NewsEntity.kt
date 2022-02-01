package com.test.newsapp.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
class NewsEntity (@PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val id:Int,
@ColumnInfo(name="title") val title:String? , @ColumnInfo(name="des") val description:String?,
@ColumnInfo(name="image_url") val image_url :String?)
