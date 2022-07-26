package com.ilya.mynewsapp.presentation.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ilya.mynewsapp.R
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.databinding.FragmentArticleBinding
import com.ilya.mynewsapp.presentation.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article.*

@AndroidEntryPoint
class ArticleFragment : BaseFragment() {

    lateinit var binding: FragmentArticleBinding
      val args:ArticleFragmentArgs by navArgs()
    private val viewModel:MainActivityViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar(article_progress_bar)

       webView.apply {
           loadUrl(args.article.url)
           webViewClient = object :WebViewClient(){
               override fun onPageFinished(view: WebView?, url: String?) {
                   super.onPageFinished(view, url)
                   hideProgressBar(article_progress_bar)
               }
           }
       }

        binding.addToSaveNews.setOnClickListener{
            viewModel.saveToDataBase(args.article)
            binding.addToSaveNews.setImageResource(R.drawable.add_full)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

}