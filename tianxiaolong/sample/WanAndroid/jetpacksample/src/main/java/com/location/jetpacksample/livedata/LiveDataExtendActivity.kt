package com.location.jetpacksample.livedata

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.location.base.JetpackBaseActivity
import com.location.jetpacksample.databinding.ActivityLivedataExtendBinding
import kotlinx.android.synthetic.main.fragment_receive_livedata.view.*
import kotlin.random.Random

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 6:34 PM
 * description：
 */


class LiveDataExtendActivity : JetpackBaseActivity() {
    private val binding by lazy { ActivityLivedataExtendBinding.inflate(LayoutInflater.from(this)) }

    private val viewmodel by viewModels<LiveDataExtendViewModel>()


    private val useridObserver = Observer<Int> {
        binding.extendLivedataId.text = "用户ID:$it"
    }

    @SuppressLint("SetTextI18n")
    private val userObserver = Observer<UserData> {
        binding.extendLivedataName.text = "姓名:${it.name}"
        binding.extendLivedataAge.text = "年龄:${it.age}"
    }


    @SuppressLint("SetTextI18n")
    private val ageObserver = Observer<String> {
        binding.extendLivedataAgeState.text = "年龄状态：$it"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.extendLivedataRegist.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewmodel.apply {
                    userDataManager.observe(this@LiveDataExtendActivity, userObserver)
                    userAge.observe(this@LiveDataExtendActivity, ageObserver)
                    userId.observe(this@LiveDataExtendActivity, useridObserver)

                }

            } else {
                viewmodel.apply {
                    userDataManager.removeObserver(userObserver)
                    userAge.removeObserver(ageObserver)
                    userId.removeObserver(useridObserver)
                }

            }
        }
    }
}