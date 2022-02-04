package com.test.newsapp.retrofit

import com.test.newsapp.BuildConfig
import com.test.newsapp.news.model.NewsBase
import retrofit2.http.GET

interface ApiService {
    @GET("1/news?apikey="+ BuildConfig.API_KEY+"&language=en")
    suspend fun getNewsList(): NewsBase

}