package com.tripbook.tripbook.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.domain.model.Location

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("locationList")
    fun setRecyclerView(recyclerView: RecyclerView, data: List<Location>) {
        val adapter = recyclerView.adapter as LocationListAdapter
        adapter.submitList(data)
    }
}