package com.test.newsapp.news.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.test.newsapp.databinding.FragmentNewsBinding
import com.test.newsapp.news.model.NewsBase

private const val ARG_NewsModel = "newsModel"
private var _binding: FragmentNewsBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!

class NewsFragment : Fragment() {
    private lateinit  var newsModel: NewsBase.resultList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsModel = it.getParcelable(ARG_NewsModel)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        setUI()
        return view
    }

    private fun setUI() {
        binding.textViewTitle.text = newsModel.title
        binding.textViewDescription.text = newsModel.description
        Glide.with(binding.newsImage.context)
            .load(newsModel.image_url)
            .into(binding.newsImage)
    }


    companion object {
        @JvmStatic
        fun newInstance(newsList: NewsBase.resultList) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_NewsModel, newsList)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}