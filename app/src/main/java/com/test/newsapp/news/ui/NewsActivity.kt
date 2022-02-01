package com.test.newsapp.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.test.newsapp.R
import com.test.newsapp.databinding.ActivityNewsBinding
import com.test.newsapp.news.adapter.News_Adapter
import com.test.newsapp.news.model.NewsBase
import com.test.newsapp.news.viewmodel.NewsViewModel
import com.test.newsapp.news.viewmodel.ViewModelFactory
import com.test.newsapp.retrofit.ApiHelper
import com.test.newsapp.retrofit.RetrofitBuilder
import com.test.newsapp.roomdatabase.NewsDataBaseViewModel
import com.test.newsapp.roomdatabase.NewsDataBaseViewModelFactory
import com.test.newsapp.utils.ApplicationClass
import com.test.newsapp.utils.CommonUtils
import com.test.newsapp.utils.Connection
import com.test.newsapp.utils.Status

class NewsActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityNewsBinding
    private lateinit var mViewModel : NewsViewModel
    private lateinit var adapter :News_Adapter
    private lateinit var mCommonUtils: CommonUtils
    private val mNewsDBViewModel: NewsDataBaseViewModel by viewModels {
        NewsDataBaseViewModelFactory((application as ApplicationClass).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeView()
        intializeMembers()
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun intializeMembers() {
        mCommonUtils = CommonUtils()
    }

    private fun setupObservers() {
        if (Connection.getInstance().isNetworkAvailable(this)) {
            mViewModel.getNewsList().observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mBinding.viewpager.visibility = View.VISIBLE
                            mBinding.progressBar.visibility = View.GONE
                            resource.data?.results?.let {
                                for (newsModel in it) {
                                    //val newsEntity = NewsEntity(0,newsModel.title,newsModel.description,newsModel.image_url)
                                    mNewsDBViewModel.insert(newsModel)
                                }
                            }

                            resource.data?.let { newsBase -> retrieveList(newsBase.results) }
                        }
                        Status.ERROR -> {
                            mBinding.viewpager.visibility = View.VISIBLE
                            mBinding.progressBar.visibility = View.GONE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            mBinding.progressBar.visibility = View.VISIBLE
                            mBinding.viewpager.visibility = View.GONE
                        }
                    }
                }
            })
        }
            else{
                mCommonUtils.showAlert(
            2000,
            resources.getString(R.string.no_internet_connection),
            resources.getString(R.string.not_connected_to_internet),
            R.drawable.wrong_icon,
            R.color.notiFailColor,
            this
        )
            // Add an observer on the LiveData returned by getAlphabetizedWords.
            // The onChanged() method fires when the observed data changes and the activity is
            // in the foreground.
            mNewsDBViewModel.allNews.observe(this) { news ->
                // Update the cached copy of the news in the adapter.
                news.let {
                    /*val mList = arrayListOf< NewsBase.resultList>()
                    for (newsEntity in it) {
                        val newsModel = newsList(newsEntity.title,newsEntity.description,newsEntity.image_url)
                        mList.add(newsModel)
                    }*/
                    mBinding.viewpager.visibility = View.VISIBLE
                    retrieveList(it)
                }
            }
            mBinding.progressBar.visibility = View.GONE
        }
    }



    private fun retrieveList(news: List<NewsBase.resultList>) {
        adapter.apply {
            addNews(news)
            notifyDataSetChanged()
        }
    }

    private fun setupUI() {

        adapter = News_Adapter(
            supportFragmentManager, arrayListOf()
        )
        mBinding.viewpager.adapter = adapter
    }

    private fun setupViewModel() {

        mViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(NewsViewModel::class.java)
    }

    private fun intializeView() {
       mBinding = ActivityNewsBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
    }
}