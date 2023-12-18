package com.tripbook.tripbook.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.tripbook.domain.model.Location
import com.tripbook.tripbook.domain.model.TempArticle

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("locationList")
    fun setLocationList(recyclerView: RecyclerView, data: List<Location>) {
        val adapter = recyclerView.adapter as LocationListAdapter
        adapter.submitList(data)
    }

    @JvmStatic
    @BindingAdapter("tempSaveList")
    fun setTempSaveList(recyclerView: RecyclerView, data: List<TempArticle>) {
        val adapter = recyclerView.adapter as TempSaveAdapter
        adapter.submitList(data)
    }
}