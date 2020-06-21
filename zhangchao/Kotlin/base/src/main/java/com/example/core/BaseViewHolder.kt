package com.example.core

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.util.HashMap

/**
 *
 * @author zhangchao
 * time：2020/6/20 22:55
 * description：
 */
@Suppress("UNCHECKED_CAST")
open class BaseViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView) {
     val viewHashMap = HashMap<Int, View>()

     fun <T : View> getView(@IdRes id: Int): T {
        var view: View? = viewHashMap[id]
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap[id] = view
        }
        return view as T
    }

    fun setText(@IdRes id :Int, msg:String){
       (getView<View>(id) as TextView).text = msg
    }


}