package com.chao.kotlin.code.bylazy

/**
 *
 * @author zhangchao
 * time：2020/7/9 21:32
 * description：
 * 属性委托
 */
class Example {
  var name : String by Delegate()
}