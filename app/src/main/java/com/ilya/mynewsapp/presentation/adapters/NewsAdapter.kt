package com.ilya.mynewsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilya.mynewsapp.databinding.NewsItemBinding
import com.ilya.mynewsapp.model.Article

class NewsAdapter(val listener: Listener): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
         return oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           NewsItemBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
            )
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            title.text = article.title
            description.text = article.description
            source.text = article.source.name
            publishedAt.text = article.publishedAt
            Glide.with(holder.itemView).load(article.urlToImage).into(img)
        }
        holder.itemView.setOnClickListener {
            listener.onClick(article.url)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface Listener{
        fun onClick(url:String)
    }
}