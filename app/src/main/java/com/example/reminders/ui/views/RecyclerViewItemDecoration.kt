package com.example.reminders.ui.views

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerViewItemDecoration(_space: Int, _firstItemTopSpace: Int) :
    RecyclerView.ItemDecoration() {
    private var space = _space
    private var firstItemTopSpace = _firstItemTopSpace

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        if (position == 0) {
            outRect.set(space, firstItemTopSpace, space, space)
        } else {
            outRect.set(space, 0, space, space)
        }
    }
}