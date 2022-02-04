package com.test.newsapp.retrofit

import com.test.newsapp.BuildConfig


class ApiHelper(private val apiService: ApiService) {

    suspend fun getNewsList() = apiService.getNewsList()
}