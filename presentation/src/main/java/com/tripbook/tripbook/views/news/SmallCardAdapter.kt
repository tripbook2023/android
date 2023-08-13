package com.tripbook.tripbook.views.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.databinding.ItemNewsCardSmallBinding
import com.tripbook.tripbook.domain.model.News

class SmallCardAdapter : ListAdapter<News, SmallCardAdapter.SmallCardViewHolder>(newsComparator){

    private companion object {
        val newsComparator = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem == newItem


        }
    }

    class SmallCardViewHolder(
        val binding: ItemNewsCardSmallBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.card = news
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallCardViewHolder =
        SmallCardViewHolder(
            ItemNewsCardSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun onBindViewHolder(holder: SmallCardViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}