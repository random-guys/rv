package com.random_guys.rv

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerScrollMoreListener(
    layoutManager: LinearLayoutManager,
    private val loadMoreListener: OnLoadMoreListener?,
    private val visibleThreshold: Int = 5
) : RecyclerView.OnScrollListener() {
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true

    private val mLayoutManager: RecyclerView.LayoutManager

    init {
        this.mLayoutManager = layoutManager
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled. This will be
     * called after the scroll has completed.
     * <p>
     * This callback will also be called if visible item range changes after a layout
     * calculation. In that case, dx and dy will be 0.
     *
     * @param rv The RecyclerView which scrolled.
     * @param dx The amount of horizontal scroll.
     * @param dy The amount of vertical scroll.
     */
    override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
        if (loadMoreListener != null) {
            var lastVisibleItemPosition = 0
            val totalItemCount = mLayoutManager.itemCount

            when (mLayoutManager) {
                is StaggeredGridLayoutManager -> {
                    val lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null)
                    lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
                }
                is LinearLayoutManager -> lastVisibleItemPosition =
                    mLayoutManager.findLastVisibleItemPosition()
                is GridLayoutManager -> lastVisibleItemPosition =
                    mLayoutManager.findLastVisibleItemPosition()
            }

            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = 0
                this.previousTotalItemCount = totalItemCount
                if (totalItemCount == 0) {
                    this.loading = true
                }
            }

            if (loading && totalItemCount > previousTotalItemCount) {
                loading = false
                previousTotalItemCount = totalItemCount
            }

            if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
                currentPage++
                loadMoreListener.onLoadMore(currentPage, totalItemCount)
                loading = true
            }
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore(page: Int, total: Int)
    }
}