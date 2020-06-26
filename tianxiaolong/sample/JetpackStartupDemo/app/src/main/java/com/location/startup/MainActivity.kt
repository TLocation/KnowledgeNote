package com.location.startup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.startup.AppInitializer
import com.location.logmodle.LogInit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test_btn.setOnClickListener {
            AppInitializer.getInstance(this)
                .initializeComponent(LogInit::class.java)

        }
    }
}
