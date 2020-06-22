package com.example.lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseViewHolder
import com.example.lesson.entity.Lesson
import java.util.*

class LessonAdapter() : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    private var list: List<Lesson> = ArrayList<Lesson>()

    fun updateAndNotify(list: List<Lesson>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder.onCreate(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    /**
     * 静态内部类
     */
    class LessonViewHolder(itemView: View) : BaseViewHolder(itemView) {
        companion object {
            fun onCreate(parent: ViewGroup): LessonViewHolder {
                return LessonViewHolder(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_lesson, parent, false))
            }
        }

        fun onBind(lesson: Lesson) {
            var date: String = lesson.date
            setText(R.id.tv_date, date)
            setText(R.id.tv_content, lesson.content)
            val state: Lesson.State = lesson.state!!
            if (state != null) {
                state.stateName()?.let { setText(R.id.tv_state, it) }
            }
            var colorRes = R.color.playback
            when (state) {
                Lesson.State.PLAYBACK -> {
                    // 即使在 {} 中也是需要 break 的。
                    colorRes = R.color.playback
                }
                Lesson.State.LIVE -> colorRes = R.color.live
                Lesson.State.WAIT -> colorRes = R.color.wait
            }
            val backgroundColor = itemView.context.getColor(colorRes)
            getView<View>(R.id.tv_state)!!.setBackgroundColor(backgroundColor)
        }


    }


}