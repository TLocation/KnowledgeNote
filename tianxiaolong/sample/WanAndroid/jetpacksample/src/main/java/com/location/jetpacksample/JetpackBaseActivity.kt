package com.location.jetpacksample

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.location.base.BaseActivity


/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:33
 * description：
 */

const val  REQUEST_CODE = 201
abstract  class JetpackBaseActivity :BaseActivity() {



    fun checkFloatAuthority() = Settings.canDrawOverlays(this)

    fun startRequestWindowPermission(){
        startActivityForResult(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ), REQUEST_CODE
        )
    }


    open fun requestAlertSuccess(){}

    open fun requestAlertFail(){}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == REQUEST_CODE){
              if(checkFloatAuthority()){
                  requestAlertSuccess()
              }else{
                  requestAlertFail()
              }
        }
    }


}