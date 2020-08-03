package com.chao.kotlin.code.by

/**
 *
 * @author zhangchao
 * time：2020/7/8 22:47
 * description：
 */
fun main(){
    val baseImpl = BaseImpl(100)

    val car = Car(baseImpl)

    car.print()
    println(car.message())


}