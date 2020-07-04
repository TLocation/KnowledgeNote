package com.chao.kotlin.code.data

/**
 *
 * @author zhangchao
 * time：2020/7/4 22:49
 * description：
 */
fun main() {
   val personData = PersonData("超哥")

    val personData1 = PersonData("龙哥")

    personData.age = 23

    personData1.age = 25

    println("${personData.age}")
    println("${personData1.age}")

    //copy,在很多情况下，我们需要复制一个对象改变它的一些属性，但其余部分保持不变。 copy() 函数就是为此而生成
    val person =  personData1.copy(name = "东哥")

    println(person.name)


    /**
     * 密封类
     */

   val name = getAnimalName(Bird("大老鹰"))

    println(name)

}
fun getAnimalName(animal :Animal): String  = when(animal){
    is Bird -> animal.name
    is Fish -> animal.name

}
sealed class Animal
class Bird(var name: String) : Animal()
class Fish(var name: String) : Animal()

/**
 * 密封类
 * 声明一个密封类在类名使用 sealed 修饰符
所有子类都必须在与密封类自身相同的文件中声明（子类的扩展类不受此控制）
一个密封类是自身抽象的，它不能直接实例化并可以有抽象（abstract）成员
密封类不允许有非-private 构造函数（其构造函数默认为 private）
 */