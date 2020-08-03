package com.chao.kotlin.code

import com.chao.kotlin.code.bean.Rectangle

/**
 *
 * @author zhangchao
 * time：2020/6/28 18:57
 * description：
 */
fun main(){
    //可变引用，这种变量的值可以被改变，这种声明对应着java的普通变量                            。
    var num = 0
    //不可变引用，这种变量不可以被改变，相当于java中final修饰的变量。
    val name = "超哥"

    println("method sample")
    val numCount = numCount(2, 3)
    val numDobule = numDobule(2, 3)
    val maxNum = maxNum(4, 6)
    println("numCount   $numCount")
    println("numDobule   $numDobule")
    println("maxNum比较大小    $maxNum")

    /**
     * 实例化一个数据类
     */

    val rectangle = Rectangle(-1,-1)
    println("比较宽度长度 ${rectangle .isSquare}")

    val createRandomRectangle = rectangle.createRandomRectangle()

    println("比较宽度长度 ${createRandomRectangle.isSquare}")
}

/**
 * kotlin中定义有返回值的方法
 */
fun numCount(a :Int ,b : Int) : Int{
    return a+b
}

/**
 * 简便写法 ,判断类型，直接返回结果
 */
fun numDobule(a :Int , b : Int) = a*b

/**
 * 比较大小
 */
fun maxNum(a : Int ,b :Int) : Int{
    return if(a > b) a else b
}
fun max(a:Int ,b :Int) : Int =  if(a > b) a else b


