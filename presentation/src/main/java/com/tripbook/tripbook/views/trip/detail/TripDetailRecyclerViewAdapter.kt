package com.tripbook.tripbook.views.trip.detail
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.TripDetailItemBinding

class TripDetailRecyclerViewAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<TripDetailRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding : TripDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: String) {
            binding.title.text = "타이틀 $item"
            binding.location.text = "위치 $item"
            binding.subTitle.text = "소제목 $item"
            binding.mainText.text = "본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문 $item"
            binding.locationImg.setImageResource(com.tripbook.tripbook.core.design.R.drawable.icn_location02_18)
            binding.img.setImageResource(R.drawable.tripbook_image)
        }
    }
}
