package com.tripbook.tripbook.views.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.databinding.ItemNewsCardBinding
import com.tripbook.tripbook.databinding.ItemNewsEventBannerBinding
import com.tripbook.tripbook.domain.model.Banner
import com.tripbook.tripbook.domain.model.News
import com.tripbook.tripbook.domain.model.ui.MainNewsUiModel

class CardAdapter : ListAdapter<MainNewsUiModel, RecyclerView.ViewHolder>(
    itemComparator
) {
    private companion object {
        val itemComparator = object : DiffUtil.ItemCallback<MainNewsUiModel>() {
            override fun areItemsTheSame(
                oldItem: MainNewsUiModel,
                newItem: MainNewsUiModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()


            override fun areContentsTheSame(
                oldItem: MainNewsUiModel,
                newItem: MainNewsUiModel
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> BannerViewHolder(
                ItemNewsEventBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> CardViewHolder(
                ItemNewsCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].viewType

    private class BannerViewHolder(
        val binding: ItemNewsEventBannerBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: Banner) {
            // 여기다가 ViewPager,
        }
    }

    private class CardViewHolder(
        val binding: ItemNewsCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: News) {
            // 여기다가 Binding 진행 및 터치 Event 장착
        }
    }

}