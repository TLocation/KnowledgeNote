package com.example.core

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("UseSparseArrays")
    private val viewHashMap: HashMap<Int, View> = HashMap()
    fun <T> getView(id: Int): T? {
        var view = viewHashMap[id]
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap[id] = view
        }
        return view as T?
    }

    fun setText(id: Int, text: String) {
        (getView<View>(id) as TextView?)!!.text = text
    }
}