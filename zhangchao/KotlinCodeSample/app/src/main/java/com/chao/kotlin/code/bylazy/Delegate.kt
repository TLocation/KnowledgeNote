package com.chao.kotlin.code.bylazy

import kotlin.reflect.KProperty

/**
 *
 * @author zhangchao
 * time：2020/7/9 21:33
 * description：
 */
class Delegate {

    operator fun getValue(example: Example, property: KProperty<*>): String {
     return "$example i am thank you , and you?"
    }

    operator fun setValue(example: Example, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $example.")

    }


}