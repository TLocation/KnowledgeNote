package com.chao.kotlin.code.enumpage

/**
 *
 * @author zhangchao
 * time：2020/7/4 23:22
 * description：
 */
fun main() {

    val color : Color = Color.BLACK;

    println(Color.values())
    println(Color.valueOf("RED"))
    println(color.name)
    println(color.ordinal)

}