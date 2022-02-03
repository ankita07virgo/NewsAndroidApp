package com.test.newsapp.roomdatabase

import androidx.lifecycle.*
import com.test.newsapp.news.model.NewsBase
import com.test.newsapp.utils.Resource
import kotlinx.coroutines.launch

class NewsDataBaseViewModel(private val repository: NewsRepository) : ViewModel() {

    private val newsList = MutableLiveData<Resource<List<NewsBase.resultList>>>()
    init {
        fetchNewsList()
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(newsEntity: NewsBase.resultList) = viewModelScope.launch {
        repository.insert(newsEntity)
    }

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    //val allNews: MutableLiveData<Resource<List<NewsBase.resultList>>>() = repository.allNews.asLiveData()



    fun getNewsList(): LiveData<Resource<List<NewsBase.resultList>>> {
        return newsList
    }

    private fun fetchNewsList() {
        viewModelScope.launch {
            newsList.postValue(Resource.loading(null))
            try {
                val usersFromDb = repository.getAllNews()
                newsList.postValue(Resource.success(usersFromDb))
                }
             catch (e: Exception) {
                // newsList.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }
}



class NewsDataBaseViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDataBaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsDataBaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}