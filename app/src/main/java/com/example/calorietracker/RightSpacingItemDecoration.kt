package com.example.calorietracker

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RightSpacingItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when (parent.getChildLayoutPosition(view)) {
            0 -> outRect.left = 38
            1 -> outRect.left = 24
            2 -> outRect.left = 56
        }
    }
}
