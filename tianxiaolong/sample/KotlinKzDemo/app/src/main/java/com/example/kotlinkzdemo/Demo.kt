package com.example.kotlinkzdemo

class Manager( val data:MutableList<String>){
    fun addData(value:String){
        data.add(value)
    }

    fun size() = data.size
}

fun Manager.addIndexData(index:Int,value: String){
    this.data.add(index,value)
}
fun Manager.printData() {
    println("list=${data.toString()}")
}

val Manager.lastValue:String
get() = data[data.size-1]


fun main(){
   val manager = Manager(mutableListOf("a","b","c"))
    println("manager data size=${manager.size()}")
    manager.addData("d")
    println("manager data size=${manager.size()}")
    manager.printData()
    manager.addIndexData(1,"e")
    println("manager data size=${manager.size()}")
    manager.printData()
    println("manager last data=${manager.lastValue}")


}

