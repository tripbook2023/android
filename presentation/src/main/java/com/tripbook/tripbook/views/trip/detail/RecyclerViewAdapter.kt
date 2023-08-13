package com.tripbook.tripbook.views.trip.detail
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.R

class RecyclerViewAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val location: TextView = itemView.findViewById(R.id.location)
        private val subTitle: TextView = itemView.findViewById(R.id.subTitle)
        private val mainText: TextView = itemView.findViewById(R.id.mainText)
        private val locaionImg : ImageView = itemView.findViewById(R.id.location_img)
        private val img : ImageView = itemView.findViewById(R.id.img)

        fun bind(item: String) {
            title.text = "타이틀 $item"
            location.text = "위치 $item"
            subTitle.text = "소제목 $item"
            mainText.text = "본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문본문 $item"
            locaionImg.setImageResource(com.tripbook.tripbook.core.design.R.drawable.icn_location02_18)
            img.setImageResource(R.drawable.tripbook_image)
        }
    }
}
