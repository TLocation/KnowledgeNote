package com.example.lesson

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.BaseView
import com.example.lesson.entity.Lesson

/**
 *
 * @author zhangchao
 * time：2020/6/20 23:33
 * description：
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter>, Toolbar.OnMenuItemClickListener {
     val lessonPresenter = LessonPresenter(this)

    override fun getPresenter(): LessonPresenter {
        return lessonPresenter
    }

     val lessonAdapter = LessonAdapter()

     var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lesson)
        toolbar.setOnMenuItemClickListener(this)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.apply {
         setLayoutManager(LinearLayoutManager(this@LessonActivity))
           setAdapter(lessonAdapter)
            addItemDecoration(DividerItemDecoration(this@LessonActivity, LinearLayout.VERTICAL))
        }


        refreshLayout = findViewById(R.id.swipe_refresh_layout)
        refreshLayout.apply {
            refreshLayout!!.setOnRefreshListener { getPresenter().fetchData() }
            refreshLayout!!.isRefreshing = true

        }

        getPresenter().fetchData()
    }

    fun showResult(lessons: ArrayList<Lesson>) {
        lessonAdapter.updateAndNotify(lessons)
        refreshLayout!!.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        getPresenter().showPlayback()
        return false
    }


}