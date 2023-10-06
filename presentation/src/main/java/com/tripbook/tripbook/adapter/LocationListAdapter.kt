package com.tripbook.tripbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.LocationListItemBinding
import com.tripbook.tripbook.domain.model.Location

class LocationListAdapter(val itemClickListener: (Int) -> Unit) :
    ListAdapter<Location, LocationListAdapter.LocationListViewHolder>(DiffUtil) {

    private var selectedPosition: Int = -1
    private var lastSelectedPosition: Int = -1

    class LocationListViewHolder(private val itemBinding: LocationListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Location) {
            itemBinding.location.text = item.place_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val binding =
            LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            itemClickListener(selectedPosition)
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if (selectedPosition == holder.adapterPosition) {
            holder.itemView.rootView.setBackgroundResource(R.color.p_1)
        } else {
            holder.itemView.rootView.setBackgroundResource(R.color.white)
        }
    }

    companion object {
        val DiffUtil = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem.place_name == newItem.place_name
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }
        }
    }
}
