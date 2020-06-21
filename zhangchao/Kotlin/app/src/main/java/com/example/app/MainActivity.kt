package com.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.lesson.LessonActivity

/**
 *
 * @author zhangchao
 * time：2020/6/20 21:01
 * description：
 */
class MainActivity : Activity(), View.OnClickListener {


    val usernameKey: String = "username"
    val passwordKey: String = "password"
    lateinit var et_username: EditText
    lateinit var et_password: EditText
    lateinit var et_code: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_username = findViewById<EditText>(R.id.et_username)
        et_password =findViewById<EditText>(R.id.et_password)
        et_code =  findViewById<EditText>(R.id.et_code)


        val btn_login = findViewById<Button>(R.id.btn_login)
        val img_code = findViewById<CodeView>(R.id.code_view)
        btn_login.setOnClickListener(this)
        img_code.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        if (view is CodeView) {
            view.updateCode()
        } else if (view is Button) {
            login()
        }
    }

    fun login() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        val code = et_code.text.toString()

        val user = User(username, password, code)
        if (verify(user)) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
            startActivity(Intent(this, LessonActivity::class.java))
        }

    }


    fun verify(user: User): Boolean {
        if ((user.username?.length ?: 0) < 4) {
            return false
        }
        if (user.password?.length ?: 0 < 4) {
            return false
        }

        return true
    }
}