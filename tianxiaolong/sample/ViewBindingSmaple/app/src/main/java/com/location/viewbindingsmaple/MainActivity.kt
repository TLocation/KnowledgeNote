package com.location.viewbindingsmaple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import com.location.viewbindingsmaple.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main_2.*

class MainActivity : AppCompatActivity() {
    lateinit var  binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        //使用viewBinding 获取View
        binding.testBtn.setOnClickListener {
            Toast.makeText(this,"测试view",Toast.LENGTH_SHORT)
        }
        //使用Kotlin方式获取view
        //kotlin可以获取任意布局的view 但是如果当前Activity fragment 设置的不是当前view 就会崩溃 不安全
        //下方代码会奔溃
//        test_2_btn.setOnClickListener {
//            Toast.makeText(this,"崩溃view",Toast.LENGTH_SHORT)
//        }

    }
}
