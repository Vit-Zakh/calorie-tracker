package com.example.calorietracker

import android.graphics.Rect
import android.util.TypedValue
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

        val metrics = view.resources.displayMetrics

        when (parent.getChildLayoutPosition(view)) {
            0 ->
                outRect.left =
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, metrics).toInt()
            1 ->
                outRect.left =
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, metrics).toInt()
            2 ->
                outRect.left =
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, metrics).toInt()
        }
    }
}
