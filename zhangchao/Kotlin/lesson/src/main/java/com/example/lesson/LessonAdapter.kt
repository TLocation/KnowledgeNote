package com.example.lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseViewHolder
import com.example.lesson.entity.Lesson

/**
 *
 * @author zhangchao
 * time：2020/6/21 13:28
 * description：
 */
class LessonAdapter : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    var list = ArrayList<Lesson>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {

        val viewHolder  = LessonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lesson,parent,false))

        return viewHolder
    }

    override fun getItemCount(): Int {
       return list.size
    }


    fun updateAndNotify(list: ArrayList<Lesson>) {
        this.list = list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {

  holder.onBind(list.get(position))
    }


     class LessonViewHolder(itemView : View) : BaseViewHolder(itemView){

         fun onBind(lesson :Lesson){
             setText(R.id.tv_date,lesson.date?:"日期不确定")

             setText(R.id.tv_content, lesson.content?:"好好好")



         }
    }
}