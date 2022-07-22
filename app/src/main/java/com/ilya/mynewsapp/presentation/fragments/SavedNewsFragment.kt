package com.ilya.mynewsapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilya.mynewsapp.R
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.databinding.FragmentSavedNewsBinding
import com.ilya.mynewsapp.presentation.adapters.NewsAdapter
import com.ilya.mynewsapp.presentation.viewmodels.MainActivityViewModel
import com.ilya.mynewsapp.utils.Constance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(),NewsAdapter.Listener {

    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var adapter: NewsAdapter
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNewsFromDataBase()
        setupRecyclerView()
        listenNews()
    }

    private fun listenNews() {
        viewModel.newsDataBase?.observe(viewLifecycleOwner){
            adapter.differ.submitList(it)
        }
    }

    private fun setupRecyclerView()= with(binding) {
        adapter  = NewsAdapter(this@SavedNewsFragment)
        recyclerViewSavedNews.adapter = adapter
        recyclerViewSavedNews.layoutManager = LinearLayoutManager(this@SavedNewsFragment.context)
    }

    override fun onClick(article: Article) {
        val bundle = Bundle()
        bundle.putSerializable(Constance.BUNDLE_KEY, article)
        findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment, bundle)
    }

}