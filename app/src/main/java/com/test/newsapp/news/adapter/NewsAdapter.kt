package com.test.newsapp.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.newsapp.databinding.ItemLayoutBinding
import com.test.newsapp.news.model.NewsBase
import com.test.newsapp.news.model.newsList

class NewsAdapter(private val newsBaseList: ArrayList<newsList>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(newsBaseList[position]){
                binding.textViewTitle.text = this.title
                binding.textViewDescription.text = this.description
                Glide.with(binding.newsImage.context)
                    .load(this.image_url)
                    .into(binding.newsImage)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsBaseList.size
    }
    fun addNews(newsBaseList: List<newsList>) {
        this.newsBaseList.apply {
            clear()
            addAll(newsBaseList)
        }

    }
}
