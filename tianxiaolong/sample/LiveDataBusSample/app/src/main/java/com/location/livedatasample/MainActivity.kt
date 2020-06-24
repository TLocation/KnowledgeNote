package com.location.livedatasample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.location.livedatasample.databinding.ActivityMainBinding

const val  DATA_KEY = "test_live_data"
class MainActivity : AppCompatActivity() {
    private  val viewBinding  by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        supportFragmentManager.commit {
                  add(R.id.layout_1,MainFragment1())
        }
        supportFragmentManager.commit {
            add(R.id.layout_2,MainFragment2())
        }

    }
}