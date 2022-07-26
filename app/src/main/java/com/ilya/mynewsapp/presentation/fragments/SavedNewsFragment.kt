package com.ilya.mynewsapp.presentation.fragments

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ilya.mynewsapp.R
import com.ilya.mynewsapp.data.model.Article
import com.ilya.mynewsapp.databinding.FragmentSavedNewsBinding
import com.ilya.mynewsapp.presentation.adapters.NewsAdapter
import com.ilya.mynewsapp.presentation.viewmodels.MainActivityViewModel
import com.ilya.mynewsapp.utils.Constance
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


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

        val itemTouchHelperClallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapter.differ.currentList[position]
                viewModel.deleteFromDataBase(article)
                Snackbar.make(view!!, "Successfully deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo"){
                        viewModel.saveToDataBase(article)
                    }.show()
            }

            @SuppressLint("UseRequireInsteadOfGet")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            this@SavedNewsFragment.context!!,
                            R.color.red
                        )
                    )
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()


                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }
        }
        ItemTouchHelper(itemTouchHelperClallBack).attachToRecyclerView(binding.recyclerViewSavedNews)
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