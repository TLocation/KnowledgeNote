package com.chao.kotlin.code.`interface`

/**
 *
 * @author zhangchao
 * time：2020/7/4 17:21
 * description：
 */
class TestImpl :DinnerListener,CinnerListener {



    //在实现多个接口的时候。有相同的实现方法。super<DinnerListener>.bar() 可以用这种方式区分。
    override fun bar() {
       super<DinnerListener>.bar()
       super<CinnerListener>.bar()

        println("tonight is eat pork ")
    }

    override fun potatoes(wight: Int, price: Int) {
        super.potatoes(wight, price)
        println("$wight, $price")
    }

    override fun tomatoes(size: Int) {
        super.tomatoes(size)
    }


}