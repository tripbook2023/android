package com.tripbook.tripbook.views.trip.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.databinding.ItemRecentSearchBinding

class SearchKeywordAdapter(
    private val onClick: (keyword: String) -> Unit,
    private val onDelete: (keyword: String) -> Unit,
) : ListAdapter<String, SearchKeywordAdapter.ViewHolder>(comparator) {
    private companion object {
        val comparator = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick = onClick,
        onDelete = onDelete
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(currentList[position])

    class ViewHolder(
        private val binding: ItemRecentSearchBinding,
        private val onClick: (keyword: String) -> Unit,
        private val onDelete: (keyword: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: String) = binding.run {
            textviewRecentSearch.text = keyword
            root.setOnClickListener {
                onClick(keyword)
            }
            imageviewCancel.setOnClickListener {
                onDelete(keyword)
            }
        }
    }

}