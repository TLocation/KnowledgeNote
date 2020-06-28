package com.location.base

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:32
 * description：
 */

fun AppCompatActivity.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

inline  fun <reified T:Activity> AppCompatActivity.startUiActivity(){
  startActivity(Intent(this,T::class.java))
}
abstract class BaseActivity :AppCompatActivity() {
}