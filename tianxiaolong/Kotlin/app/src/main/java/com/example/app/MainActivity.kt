package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.toast
import com.example.lesson.LessonActivity

/**
 *
 * @author tianxiaolong
 * time：2020/6/20 8:31 PM
 * description：
 */
class MainActivity :AppCompatActivity(),android.view.View.OnClickListener{

    private val usernameKey = "username"
    private  val passwordKey = "password"

    private  val et_username  by lazy {  findViewById<EditText>(R.id.et_username) }
    private  val et_password  by lazy {  findViewById<EditText>(R.id.et_password) }
    private  val et_code  by lazy {  findViewById<EditText>(R.id.et_code) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_username.setText(CacheUtils.get(usernameKey))
        et_password.setText(CacheUtils.get(passwordKey))
        findViewById<View>(R.id.btn_login).setOnClickListener(this)
        findViewById<View>(R.id.code_view).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v){
             is CodeView -> v.updateCode()
             is Button -> login()
        }

    }

    private  fun login(){
        val userName = et_username.text.toString()
        val password = et_password.text.toString()
        val code = et_code.text.toString()
        val user = User(userName,password,code)
        if(verify(user)){
            CacheUtils.save(usernameKey,userName)
            CacheUtils.save(passwordKey,password)
            startActivity(Intent(this,LessonActivity::class.java))
        }

    }

    private  fun verify(user:User):Boolean{

        if(user.username?.length?:0<4){
            toast("用户名不合法")
            return  false
        }
        if(user.password?.length?:0<4){
            toast("密码不合法")
            return  false
        }
        return  true
    }
}