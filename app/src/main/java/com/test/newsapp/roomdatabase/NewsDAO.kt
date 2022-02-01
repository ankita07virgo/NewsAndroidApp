package com.test.newsapp.roomdatabase

import androidx.room.*
import com.test.newsapp.news.model.NewsBase
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {

@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insert(newsEntity: NewsBase.resultList)

    @Query("SELECT * FROM news_table")
    fun getAllNews(): Flow<List<NewsBase.resultList>>

}