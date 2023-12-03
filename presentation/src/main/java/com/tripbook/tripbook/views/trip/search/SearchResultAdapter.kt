package com.tripbook.tripbook.views.trip.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.databinding.ItemSearchTripBinding
import com.tripbook.tripbook.domain.model.ArticleDetail

class SearchResultAdapter(
    private val onItemClick: (id: Long) -> Unit
) : ListAdapter<ArticleDetail, SearchResultAdapter.ViewHolder>(comparator){

    private companion object {
        val comparator = object : DiffUtil.ItemCallback<ArticleDetail>() {
            override fun areItemsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticleDetail,
                newItem: ArticleDetail
            ): Boolean = oldItem == newItem
        }
    }

    class ViewHolder(
        private val binding: ItemSearchTripBinding,
        private val onItemClick: (id: Long) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleDetail) = binding.run {
            article = item
            root.setOnClickListener {
                onItemClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemSearchTripBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClick = onItemClick
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}