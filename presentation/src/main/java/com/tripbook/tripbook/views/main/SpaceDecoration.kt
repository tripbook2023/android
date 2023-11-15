package com.tripbook.tripbook.views.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceDecoration(private val verticalHeight: Int, private val horizontalWidth: Int = 0): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalHeight
        outRect.right = horizontalWidth
    }
}