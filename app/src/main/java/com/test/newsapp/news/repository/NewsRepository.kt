package com.test.newsapp.news.repository

import com.test.newsapp.retrofit.ApiHelper

class NewsRepository(private val apiHelper: ApiHelper) {
    suspend fun getNewsList() = apiHelper.getNewsList()
}