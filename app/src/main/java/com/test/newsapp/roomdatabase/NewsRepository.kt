package com.test.newsapp.roomdatabase

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.test.newsapp.news.model.NewsBase
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NewsRepository(private val newsDAO: NewsDAO) {



    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(news: NewsBase.resultList) {
        newsDAO.insert(news)
    }

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    //val allNews: Flow<List<NewsBase.resultList>> = newsDAO.getAllNews()
     suspend fun getAllNews(): List<NewsBase.resultList> = newsDAO.getAllNews()
}