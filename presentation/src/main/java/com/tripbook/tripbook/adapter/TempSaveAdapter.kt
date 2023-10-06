package com.tripbook.tripbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.TempSaveItemBinding
import com.tripbook.tripbook.views.tripAdd.TempSaveItem

class TempSaveAdapter(val onClickDelete: (Int) -> Unit, val itemClickListener: (Int) -> Unit) :
    ListAdapter<TempSaveItem, TempSaveAdapter.TempSaveViewHolder>(DiffUtil) {

    private var selectedPosition: Int = -1
    private var lastSelectedPosition: Int = -1

    inner class TempSaveViewHolder(private val itemBinding: TempSaveItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: TempSaveItem, position: Int) {
            itemBinding.tempTitle.text = item.title
            itemBinding.tempDate.text = item.date
            itemBinding.deleteButton.setOnClickListener {
                // 둘 중 하나 이용해 아이템 삭제 -> 임시저장 관련 API 연결시 적용할 예정
                deleteItem(position)
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

    fun deleteItem(index: Int){
        onClickDelete(index)
    }

    companion object {
        val DiffUtil = object : DiffUtil.ItemCallback<TempSaveItem>() {
            override fun areItemsTheSame(oldItem: TempSaveItem, newItem: TempSaveItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: TempSaveItem, newItem: TempSaveItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

