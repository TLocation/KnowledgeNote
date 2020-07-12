package com.chao.kotlin.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chao.kotlin.code.R
import com.chao.kotlin.code.bylazy.Preference

class MainActivity : AppCompatActivity() {

    var name  by Preference("key","chaoge")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "  "+name)
    }
}
