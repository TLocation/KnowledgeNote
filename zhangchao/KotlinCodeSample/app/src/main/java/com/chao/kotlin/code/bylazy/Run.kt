package com.chao.kotlin.code.bylazy

/**
 *
 * @author zhangchao
 * time：2020/7/9 21:38
 * description：
 */
fun main(){

    val example = Example()

//    example.name = "超哥"
//    println(example.name)

    println(lazyValue)
    println(lazyValue)

//    var location  : String by Preference("car","auto")
//
//    println(location)


}

val lazyValue: String by lazy {
    println("computed!")
    "超哥最帅"
}