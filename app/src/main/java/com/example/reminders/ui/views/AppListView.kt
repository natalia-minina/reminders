package com.example.reminders.ui.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View


class AppListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var emptyView: View? = null

    private val emptyObserver: AdapterDataObserver = object : AdapterDataObserver() {

        fun setEmptyView() {
            val adapter = adapter
            if (adapter != null && emptyView != null) {
                if (adapter.itemCount == 0) {
                    emptyView?.setVisibility(View.VISIBLE)
                    this@AppListView.visibility = View.GONE
                } else {
                    emptyView?.setVisibility(View.GONE)
                    this@AppListView.visibility = View.VISIBLE
                }
            }
        }

        override fun onChanged() {
            setEmptyView()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            setEmptyView()
        }

        override fun onItemRangeChanged(
            positionStart: Int,
            itemCount: Int,
            payload: Any?
        ) {
            this.onItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            setEmptyView()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            setEmptyView()
        }

        override fun onItemRangeMoved(
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            setEmptyView()
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
    }

}