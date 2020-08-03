package com.chao.kotlin.code.constructor

/**
 *
 * @author zhangchao
 * time：2020/7/4 16:44
 * description：
 */
class China(name :String) : Person(name){

    override fun getName(){
        // 加上super之后就可以调用到超类的实现方法了，如果不用就不会调用到。
        super.getName()
        println("张总")
    }
}