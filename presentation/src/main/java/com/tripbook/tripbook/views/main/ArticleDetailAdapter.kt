package com.tripbook.tripbook.views.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.databinding.ItemMainCardBinding
import com.tripbook.tripbook.domain.model.ArticleDetail

class ArticleDetailAdapter(
    private val onItemClicked: (Long) -> Unit
) : PagingDataAdapter<ArticleDetail, ArticleDetailAdapter.ViewHolder>(itemComparator) {
    private companion object {
        val itemComparator = object : DiffUtil.ItemCallback<ArticleDetail>() {
            override fun areItemsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticleDetail,
                newItem: ArticleDetail
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMainCardBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClicked = {
            onItemClicked(it.id)
        })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemMainCardBinding,
        private val onItemClicked: (ArticleDetail) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(articleDetail: ArticleDetail?) {
            articleDetail?.let { detail ->
                binding.article = articleDetail
                binding.root.setOnClickListener {
                    onItemClicked(detail)
                }
            }
        }
    }
}