package com.test.newsapp.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.newsapp.databinding.ActivityNewsBinding
import com.test.newsapp.news.adapter.NewsAdapter
import com.test.newsapp.news.model.NewsBase
import com.test.newsapp.news.model.newsList
import com.test.newsapp.news.viewmodel.NewsViewModel
import com.test.newsapp.news.viewmodel.ViewModelFactory
import com.test.newsapp.retrofit.ApiHelper
import com.test.newsapp.retrofit.RetrofitBuilder
import com.test.newsapp.utils.Status

class NewsActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityNewsBinding
    private lateinit var mViewModel : NewsViewModel
    private lateinit var adapter :NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeView()
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getNewsList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mBinding.recyclerView.visibility = View.VISIBLE
                        mBinding.progressBar.visibility = View.GONE
                        resource?.data?.let { newsBase -> retrieveList(newsBase.results) }
                    }
                    Status.ERROR -> {
                        mBinding.recyclerView.visibility = View.VISIBLE
                        mBinding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                        mBinding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(news: List<newsList>) {
        adapter.apply {
            addNews(news)
            notifyDataSetChanged()
        }
    }

    private fun setupUI() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(arrayListOf())
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                mBinding.recyclerView.context,
                (mBinding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        mBinding.recyclerView.adapter = adapter
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