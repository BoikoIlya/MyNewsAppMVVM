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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(), NewsAdapter.Listener {

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
        setupRecyclerView()
        listenNews()
    }

    private fun setupRecyclerView() = with(binding){
      adapter = NewsAdapter(this@BreakingNewsFragment)
        recyclerViewBreakingNews.adapter = adapter
        recyclerViewBreakingNews.layoutManager = LinearLayoutManager(activity)
    }

    fun listenNews()
    {
        viewModel.newsApi.observe(viewLifecycleOwner){ Response->
            adapter.differ.submitList(
                Response.articles
            )
        }
    }

    override fun onClick(url: String) {
       val bundle = Bundle().apply {
           putString("webUrl", url)
       }
        findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment, bundle)
    }

}