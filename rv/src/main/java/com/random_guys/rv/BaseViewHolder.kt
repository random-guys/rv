package com.random_guys.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @JvmOverloads
    open fun onBind(position: Int = adapterPosition) {

    }
}