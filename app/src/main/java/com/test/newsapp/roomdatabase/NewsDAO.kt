package com.test.newsapp.roomdatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {

@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insert(newsEntity: NewsEntity)

    @Query("SELECT * FROM news_table")
    fun getAllNews(): Flow<List<NewsEntity>>
}