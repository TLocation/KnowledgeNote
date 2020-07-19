package com.example.designpatterns.singleton

/*
*
*   饿汉模式
*
* */
class HungryManPattern {
    private val sington: HungryManPattern = HungryManPattern()
    private fun Sington() {}
    fun getInstance(): HungryManPattern? {
        return sington
    }
}