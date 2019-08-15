package com.random_guys.rv

/**
 * Interface definition for a callback to be invoked when next part of items need to be loaded.
 */
interface LoadMoreListener {

    /**
     * Fires when user scrolled to the end of list.
     *
     * @param page            next page to download.
     * @param total current items count.
     */
    fun onLoadMore(page: Int, total: Int)
}