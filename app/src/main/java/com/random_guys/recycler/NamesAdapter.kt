package com.random_guys.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.random_guys.rv.BaseViewHolder
import com.random_guys.rv.RV

class NamesAdapter(private val mContext: Context) : RV<BaseViewHolder>() {

    private val names: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.name_item, parent, false)
        )
    }

    fun get(position: Int): String = names[position]

    fun add(item: String) {
        names.add(item)
    }

    fun add(contacts: Collection<String>) {
        this.names.addAll(contacts)
    }

    fun remove(index: Int) {
        names.removeAt(index)
    }

    fun clear() {
        names.clear()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return if (names.size > 0) {
            names.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View) :
        BaseViewHolder(itemView) {

        override fun onBind(position: Int) {
            super.onBind(position)
            (itemView as TextView).text = names[position]
        }
    }
}