package com.example.kotlinkzdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_xml.*
import java.time.Duration


fun Activity.toast(msg:String,duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,msg,duration).show()
}
fun Float.dp2px():Float{
    return  this*1.2f
}
val Float.dp:Float
get() = this*1.2f

inline  fun <reified  T :Activity> Activity.startUiActivity(args:Bundle? = null){
    startActivity(Intent(this,T::class.java)
            .apply {

                    args?.let {


                    }
            })
}
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         toast("dsa")
        toast("dsadsa")
        main_btn.setText("ss")
        //fragment_btn.setText("dsa")

        startUiActivity<TestAct>(bundleOf("name" to "mike"))
        supportFragmentManager.addTask {


        }
    }
}


