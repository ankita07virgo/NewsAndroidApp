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
                            mCommonUtils.dismissProgress()
                            resource.data?.results?.let {
                                for (newsModel in it) {
                                    mNewsDBViewModel.insert(newsModel)
                                }
                            }

                            resource.data?.let { newsBase -> retrieveList(newsBase.results) }
                        }
                        Status.ERROR -> {
                            mBinding.viewpager.visibility = View.VISIBLE
                            mCommonUtils.dismissProgress()
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            mCommonUtils.showProgress(this)
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
            mNewsDBViewModel.getNewsList().observe(this, {
                when (it.status) {
                    Status.SUCCESS -> {
                        mCommonUtils.dismissProgress()
                        if(it.data?.isEmpty() == true){
                            mCommonUtils.ToastPrint(this,resources.getString(R.string.access_app_first_time_offline_mode))
                        }
                        else {
                            it.data?.let { newsList -> retrieveList(newsList) }
                            mBinding.viewpager.visibility = View.VISIBLE
                        }
                    }
                    Status.LOADING -> {
                        mCommonUtils.showProgress(this)
                        mBinding.viewpager.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        mCommonUtils.dismissProgress()
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })

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