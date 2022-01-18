package com.rysanek.sportsfandom.domain.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ListsDiffUtil<T>(private val oldList: List<T>, private val newList: List<T>): DiffUtil.Callback() {
    
    override fun getOldListSize() = oldList.size
    
    override fun getNewListSize() = newList.size
    
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
    
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList [newItemPosition]
    }
    
    /**
     * Handles calculating the difference between two lists and updating the UI accordingly.
     * @param adapter The [RecyclerView.Adapter] to which the changes will dispatch to.
     */
    fun <R: RecyclerView.ViewHolder> calculateDiff(adapter: RecyclerView.Adapter<R>) =
        DiffUtil.calculateDiff(this).dispatchUpdatesTo(adapter)
}