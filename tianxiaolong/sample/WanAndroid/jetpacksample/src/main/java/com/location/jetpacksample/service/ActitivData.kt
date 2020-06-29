package com.location.jetpacksample.service

/**
 *
 * @author tianxiaolong
 * time：2020/6/29 11:39 PM
 * description：
 */
class ActitivData(var msg:String?=null) {
    var prv:ActitivData? = null
    var next:ActitivData? = null
}