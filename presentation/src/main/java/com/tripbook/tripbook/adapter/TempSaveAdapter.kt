package com.tripbook.tripbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.TempSaveItemBinding
import com.tripbook.tripbook.domain.model.TempArticle
import com.tripbook.tripbook.utils.convertDateFormat

class TempSaveAdapter(val onClickDelete: (Int) -> Unit, val itemClickListener: (Int) -> Unit) :
    ListAdapter<TempArticle, TempSaveAdapter.TempSaveViewHolder>(DiffUtil) {

    private var selectedPosition: Int = -1
    private var lastSelectedPosition: Int = -1

    inner class TempSaveViewHolder(private val itemBinding: TempSaveItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: TempArticle, position: Int) {
            itemBinding.tempTitle.text = item.title
            itemBinding.tempDate.text = item.updatedAt.convertDateFormat()
            itemBinding.deleteButton.setOnClickListener {
                onClickDelete(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempSaveViewHolder {
        val binding =
            TempSaveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TempSaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TempSaveViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)

        holder.itemView.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            itemClickListener(selectedPosition)
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if (selectedPosition == holder.adapterPosition) {
            holder.itemView.rootView.setBackgroundResource(R.drawable.bg_temp_clicked)
        } else {
            holder.itemView.rootView.setBackgroundResource(R.drawable.bg_temp_normal)
        }
    }

    companion object {
        val DiffUtil = object : DiffUtil.ItemCallback<TempArticle>() {
            override fun areItemsTheSame(oldItem: TempArticle, newItem: TempArticle): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: TempArticle, newItem: TempArticle): Boolean {
                return oldItem == newItem
            }
        }
    }
}

