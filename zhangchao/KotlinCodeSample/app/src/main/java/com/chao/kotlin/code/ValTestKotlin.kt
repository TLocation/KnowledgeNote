package com.chao.kotlin.code

import android.widget.Button

/**
 *
 * @author zhangchao
 * time：2020/7/1 22:29
 * description：
 */
fun main() {
   var num : Int? = null;

    val num1 = num?.let { setNum(it) }
    println("$num1")

    var str : String? = null

    //首先，你可以显式检测 b 是否为 null，这样就不会报错了。只是运行的时候不会报错了。
    if(str!=null){
        val length = if(str!=null) str.length else -1
    }
   //安全调用带？ 号的空属性. 这样安全调用，如果为空，直接返回null。程序不会崩溃
    val length = str?.length
    println("$length")

//-----------------------------------
//    Elvis 操作符 ?:
   var elvis : String? = null
   // 简单的if表达式，判断是否为空 否则返回-1
   val lengthElvis = if(elvis!=null) elvis.length else -1
    // 还有一种更简单的表达方式  ?: 这里是当左侧肯定为空时，右边才会走 -1 这个值。
    val lengthElvis_1 = elvis?.length ?: -1

    //安全的类型转换
    //如果对象不是目标类型，那么常规类型转换可能会导致 ClassCastException。 另一个选择是使用安全的类型转换，如果尝试转换不成功则返回 null：

    val aInt: Int? = elvis as? Int


    for(i in 1..10){

        println("$i")
    }

}


fun setNum(num : Int) : Int?{
    return num;
}