package com.chao.kotlin.code.data

/**
 *
 * @author zhangchao
 * time：2020/7/4 22:45
 * description：
 */
/**
 * 为了确保生成的代码的一致性以及有意义的行为，数据类必须满足以下要求：
主构造函数需要至少有一个参数；
主构造函数的所有参数需要标记为 val 或 var；
数据类不能是抽象、开放、密封或者内部的；
（在1.1之前）数据类只能实现接口。
 */
data class PersonData(val name :String){
    var sex : String? = null
    var age : Int? = null

}