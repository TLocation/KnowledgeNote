package com.location.wanandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.location.wanandroid.databean.ArticleData
import com.location.wanandroid.databean.DataX
import com.location.wanandroid.databinding.ItemArticleBinding

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 4:58 PM
 * description：
 */
class ArticleAdapter(val itemClick:(DataX) -> Unit): ListAdapter<DataX, ArticleAdapter.ViewHolder>(GardenPlantDiffCallback()) {

      class  ViewHolder(val binding:ItemArticleBinding,itemview:View):RecyclerView.ViewHolder(itemview){

        fun bind(data:DataX){
            binding.textView.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(inflate, inflate.root)
        viewHolder.itemView.setOnClickListener {
            itemClick(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
    }
}

private class GardenPlantDiffCallback : DiffUtil.ItemCallback<DataX>() {

    override fun areItemsTheSame(
        oldItem: DataX,
        newItem: DataX
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DataX,
        newItem: DataX
    ): Boolean {
        return oldItem == newItem
    }
}