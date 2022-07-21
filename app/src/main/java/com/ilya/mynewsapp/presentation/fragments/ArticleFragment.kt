package com.ilya.mynewsapp.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.ilya.mynewsapp.R
import com.ilya.mynewsapp.databinding.FragmentArticleBinding
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment() {

    lateinit var binding: FragmentArticleBinding
     private val webUrl:ArticleFragmentArgs by navArgs()

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
       webView.apply {
           loadUrl(webUrl.webUrl)
           webViewClient = WebViewClient()
       }
    }

}