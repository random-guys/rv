package com.random_guys.rv

import androidx.recyclerview.widget.RecyclerView

abstract class RV<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>(),
    RecyclerScrollMoreListener.OnLoadMoreListener {

    var loadMoreListener: LoadMoreListener? = null

    override fun onLoadMore(page: Int, total: Int) {
        loadMoreListener?.onLoadMore(page, total)
    }
}