package com.chao.kotlin.code.by

/**
 *
 * @author zhangchao
 * time：2020/7/8 22:42
 * description：
 */
class BaseImpl(var a : Int) : Base {
    override fun message(): String = "龙哥最屌 $a"

    override fun print()  = println(message())



}