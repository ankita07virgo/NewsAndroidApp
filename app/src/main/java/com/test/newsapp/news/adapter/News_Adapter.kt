package com.test.newsapp.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.test.newsapp.news.model.NewsBase
import com.test.newsapp.news.ui.NewsFragment


class News_Adapter(
    fragmentManager: FragmentManager,
    newsList: ArrayList<NewsBase.resultList>
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val newsList = ArrayList<NewsBase.resultList>()


    init {
        this.newsList.apply{
            clear()
            addAll(newsList)
        }
    }



    fun addNews(newsBaseList: List<NewsBase.resultList>) {
        this.newsList.apply {
            clear()
            addAll(newsBaseList)

        }
    }


    override fun getCount(): Int {
        return newsList.size
    }

    override fun getItem(position: Int): Fragment {
        return NewsFragment.newInstance(newsList[position])
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

}