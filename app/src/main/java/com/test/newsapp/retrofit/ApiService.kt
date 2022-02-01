package com.test.newsapp.retrofit

import com.test.newsapp.news.model.NewsBase
import retrofit2.http.GET

interface ApiService {
    @GET("news?apikey=pub_39584661b12b3ac98b29f48974e8e3f17145&language=en")
    suspend fun getNewsList(): NewsBase

}