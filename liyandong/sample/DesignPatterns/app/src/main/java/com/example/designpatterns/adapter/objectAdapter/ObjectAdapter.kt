package com.example.designpatterns.adapter.objectAdapter

/**

 * 作者：李艳东

 * 日期：on 2020/7/19 15:13

 */
class ObjectAdapter : Target {
    private var adapter: Adapter? = null

    constructor(adapter: Adapter?) {
        this.adapter = adapter
    }

    override fun request() {
        adapter!!.specificRequest()
    }
}