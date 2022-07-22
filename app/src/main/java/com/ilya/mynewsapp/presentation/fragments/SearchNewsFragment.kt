package com.ilya.mynewsapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilya.mynewsapp.R
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.databinding.FragmentSearchNewsBinding
import com.ilya.mynewsapp.presentation.adapters.NewsAdapter
import com.ilya.mynewsapp.presentation.viewmodels.MainActivityViewModel
import com.ilya.mynewsapp.utils.Constance
import com.ilya.mynewsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment(), NewsAdapter.Listener {

    private lateinit var binding: FragmentSearchNewsBinding
    private val viewModel:MainActivityViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        listenNews()
        newsEntering()
    }

    private fun listenNews() {
        viewModel.searchNewsApi.observe(viewLifecycleOwner){resource->
            when(resource){
                is Resource.Loading->
                {
                    showProgressBar(search_news_progress_bar)
                }
                is Resource.Success-> {
                    hideProgressBar(search_news_progress_bar)
                    adapter.differ.submitList(
                        resource.data?.articles
                    )
                }
                is Resource.Error->{
                    hideProgressBar(search_news_progress_bar)
                    showAlert(resource.message.toString())
                }
            }
        }
    }

    private fun newsEntering(){
        var job:Job?=null
        binding.searchEditText.addTextChangedListener{ text->
        job?.cancel()
            job =  lifecycleScope.launch{
                delay(500L)
                text?.let {
                    if (binding.searchEditText.text.isNotEmpty())
                        viewModel.searchCheckedNewsFromApi(text.toString())
                }
            }
        }
    }

    private fun setupRecyclerView()= with(binding) {
       adapter = NewsAdapter(this@SearchFragment)
        recyclerSearchNews.adapter = adapter
        recyclerSearchNews.layoutManager = LinearLayoutManager(this@SearchFragment.context)
    }

    override fun onClick(article: Article) {
        val bundle = Bundle()
        bundle.putSerializable(Constance.BUNDLE_KEY, article)
        findNavController().navigate(R.id.action_searchFragment_to_articleFragment, bundle)
    }
}