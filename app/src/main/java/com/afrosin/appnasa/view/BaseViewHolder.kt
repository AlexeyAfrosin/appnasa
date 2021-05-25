package com.afrosin.appnasa.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.afrosin.appnasa.model.ToDoItem

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(dataItem: Pair<ToDoItem, Boolean>)
}