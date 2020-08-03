package com.chao.kotlin.code.bean

import java.util.*

/**
 *
 * @author zhangchao
 * time：2020/6/28 22:28
 * description：
 * kotlin 中的类 setter 与 getter
 */
class Rectangle(val width: Int ,val height: Int) {

    val isSquare: Boolean
    get() {
        return width == height
    }

    fun createRandomRectangle() : Rectangle{
        val random = Random()
        return Rectangle(random.nextInt(),random.nextInt())
    }
}