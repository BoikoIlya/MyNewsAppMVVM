package com.ilya.mynewsapp.presentation.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.ilya.mynewsapp.databinding.FragmentArticleBinding
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : BaseFragment() {

    lateinit var binding: FragmentArticleBinding
     private val arguments:ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }


    @SuppressLint("WebViewApiAvailability")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar(article_progress_bar)
       webView.apply {
           loadUrl(arguments.webUrl)
           webViewClient = object :WebViewClient(){
               override fun onPageFinished(view: WebView?, url: String?) {
                   super.onPageFinished(view, url)
                   hideProgressBar(article_progress_bar)
               }
           }
       }
    }

}