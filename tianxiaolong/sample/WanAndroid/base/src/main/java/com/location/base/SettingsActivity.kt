package com.location.base

import android.os.Bundle
import android.view.LayoutInflater
import com.location.base.databinding.ActivitySettingsBinding

/**
 *
 * @author tianxiaolong
 * time：2020/6/30 11:38 PM
 * description：
 */
class SettingsActivity:JetpackBaseActivity() {
    private  val binding:ActivitySettingsBinding by lazy { ActivitySettingsBinding.inflate(LayoutInflater.from(this)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.settingsObserverAct.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked && !checkFloatAuthority()){
                startRequestWindowPermission()
            }else if(isChecked){
                SharePreferencesUtils.put(OPEN_OBSERVATION_ACTIVITY,isChecked)
            }else if(!isChecked){
                SharePreferencesUtils.put(OPEN_OBSERVATION_ACTIVITY,isChecked)
            }

        }
        binding.settingsObserverAct.isChecked = SharePreferencesUtils.get(OPEN_OBSERVATION_ACTIVITY,false)!!

    }

    override fun requestAlertFail() {
        super.requestAlertFail()
        SharePreferencesUtils.put(OPEN_OBSERVATION_ACTIVITY,false)
        binding.settingsObserverAct.isChecked = false
    }

    override fun requestAlertSuccess() {
        super.requestAlertSuccess()
        SharePreferencesUtils.put(OPEN_OBSERVATION_ACTIVITY,true)
        binding.settingsObserverAct.isChecked = true
    }
}