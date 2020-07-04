package com.chao.kotlin.code.`interface`

/**
 *
 * @author zhangchao
 * time：2020/7/4 17:14
 * description：
 */
interface DinnerListener {
    fun bar(){
        println("DinnerListener")
    }

    fun tomatoes(size :Int){}

    fun potatoes(wight : Int , price : Int){

    }

}