package com.test.newsapp.retrofit


class ApiHelper(private val apiService: ApiService) {

    suspend fun getNewsList() = apiService.getNewsList()
}