package com.chao.kotlin.code.constructor

/**
 *
 * @author zhangchao
 * time：2020/7/4 16:25
 * description：
 */
/**
 * 在类中的构造方法加入方法属性 val var 等同于 在类中的 val name :String .
 * 相当于在构造方法中初始化了。
 */
// 主构造器
open class Person (val name :String,var sex: String,var age: Int){

    constructor(name :String,sex :String) :this(name,sex,0){

    }
    constructor(name :String) :this(name,"",0){

    }


   open fun getName() {

       println("getName")
    }



}