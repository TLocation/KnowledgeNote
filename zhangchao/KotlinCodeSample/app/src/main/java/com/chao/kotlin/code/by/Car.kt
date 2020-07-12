package com.chao.kotlin.code.by

/**
 *
 * @author zhangchao
 * time：2020/7/8 22:44
 * description：
 * 类委托，也是接口委托
 * 在委托中重写接口中的方法，并赋值。在调用时会直接调用委托的结果，
 * 会把接口中定义的值覆盖。
 */
class Car(b : Base) : Base by b{
    override fun message() = "超哥最帅"


}