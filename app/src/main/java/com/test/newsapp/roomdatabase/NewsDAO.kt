package com.test.newsapp.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.newsapp.news.model.NewsBase
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsEntity: NewsBase.resultList)

    @Query("SELECT * FROM news_table")
    suspend fun getAllNews(): List<NewsBase.resultList>


}