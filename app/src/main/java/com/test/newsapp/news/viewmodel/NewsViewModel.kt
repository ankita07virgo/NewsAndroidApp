package com.test.newsapp.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.test.newsapp.news.model.NewsBase
import com.test.newsapp.news.repository.NewsRepository
import com.test.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    //val sample = MutableLiveData<String>()
    fun getNewsList() : LiveData<Resource<NewsBase>> {
       return  liveData(IO) {

            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = newsRepository.getNewsList()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }


}