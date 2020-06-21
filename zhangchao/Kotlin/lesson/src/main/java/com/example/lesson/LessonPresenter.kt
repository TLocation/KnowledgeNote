package com.example.lesson

import android.os.Build
import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient
import com.example.core.utils.CacheUtils
import com.example.core.utils.Utils
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

/**
 *
 * @author zhangchao
 * time：2020/6/20 23:20
 * description：
 */
open class LessonPresenter(val lessonActivity: LessonActivity)  {

    private val LESSON_PATH = "lessons"



    private var lessons: List<Lesson> = ArrayList()

    private val type = object : TypeToken<List<Lesson>>() {

    }.type

    fun fetchData() {
        HttpClient.INSTANCE.get(LESSON_PATH, type, object  : EntityCallback<ArrayList<Lesson>> {
            override fun onSuccess(lessons: ArrayList<Lesson>) {
                lessonActivity.runOnUiThread { lessonActivity.showResult(lessons) }
            }

            override fun onFailure(message: String?) {
                lessonActivity.runOnUiThread {

                    Utils.toast(message!!)
                }

            }
        })
    }

    fun showPlayback() {
        val playbackLessons = ArrayList<Lesson>()
        for (lesson in lessons) {
            if (lesson.state === Lesson.State.PLAYBACK) {
                playbackLessons.add(lesson)
            }
        }
        lessonActivity.showResult(playbackLessons)
    }

}