package com.test.newsapp.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.test.newsapp.news.model.newsList
import com.test.newsapp.news.ui.NewsFragment


class News_Adapter(
    fragmentManager: FragmentManager,
    newsList: ArrayList<newsList>
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val newsList = ArrayList<newsList>()


    init {
        this.newsList.clear()
        this.newsList.addAll(newsList)
    }



    fun addNews(newsBaseList: List<newsList>) {
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