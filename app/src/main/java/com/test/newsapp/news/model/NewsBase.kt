package com.test.newsapp.news.model

data class NewsBase(val status : String,
                    val totalResults : Int,
                    val results : List<newsList>,
                    val nextPage : Int)
