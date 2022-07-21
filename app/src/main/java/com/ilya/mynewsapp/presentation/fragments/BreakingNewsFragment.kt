package com.ilya.mynewsapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilya.mynewsapp.R
import com.ilya.mynewsapp.databinding.FragmentBreakingNewsBinding
import com.ilya.mynewsapp.presentation.adapters.NewsAdapter
import com.ilya.mynewsapp.presentation.viewmodels.MainActivityViewModel
import com.ilya.mynewsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment : BaseFragment(), NewsAdapter.Listener {

    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var adapter: NewsAdapter

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar(breaking_news_progress_bar)
        setupRecyclerView()
        listenNews()
    }

    private fun setupRecyclerView() = with(binding){
      adapter = NewsAdapter(this@BreakingNewsFragment)
        recyclerViewBreakingNews.adapter = adapter
        recyclerViewBreakingNews.layoutManager = LinearLayoutManager(activity)
    }

    private fun listenNews()
    {
        viewModel.newsApi.observe(viewLifecycleOwner){ resource->
            when(resource){
                is Resource.Loading->showProgressBar(breaking_news_progress_bar)
                is Resource.Success-> {
                    hideProgressBar(breaking_news_progress_bar)
                    adapter.differ.submitList(
                        resource.data?.articles
                    )
                }
                is Resource.Error->{
                        hideProgressBar(breaking_news_progress_bar)
                        showAlert(resource.message.toString())
                }
            }
        }
    }

    override fun onClick(url: String) {
       val bundle = Bundle().apply {
           putString("webUrl", url)
       }
        findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment, bundle)
    }

}