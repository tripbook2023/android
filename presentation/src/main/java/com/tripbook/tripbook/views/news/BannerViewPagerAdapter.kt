package com.tripbook.tripbook.views.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tripbook.tripbook.databinding.ItemBannerCardsBinding
import com.tripbook.tripbook.domain.model.Banner

internal class BannerViewPagerAdapter : ListAdapter<Banner, BannerViewPagerAdapter.BannerViewHolder>(
    bannerComparator
){

    private companion object {
        val bannerComparator = object : ItemCallback<Banner>() {
            override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
        BannerViewHolder(ItemBannerCardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) =
        holder.bind(currentList[position])


    class BannerViewHolder(
        val binding: ItemBannerCardsBinding
    ): ViewHolder(binding.root) {
        fun bind(item: Banner) {
            binding.banner = item
        }
    }
}