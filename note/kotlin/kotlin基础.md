# Kotlin基础

作者：田晓龙                                                                                                                                                              编辑时间：2020/06/20

---

## 变量声明

**先看在Java中的变量声明**

```java
int age =  12;
String sex = "男";
final String name = "Mike";
String like = null;  
```

**再看在Kotlin中的变量修饰**

```kotlin
var age:Int = 12
var sex = "男"
val name = "Mike"
var like:String? = null
```

- 在kotlin中声明变量变为了**val var**
- kotlin中变量名和变量类型发生了置换 并且加了**:**连接符
- 并且没有了；分号（kotlin中代码结尾不需要写分号）

### 1. val var

kotlin中 val 代表声明常量 对应Java中的final修饰符

kotlin中 var 代表变量 即可修改 

### 2. 空安全设计

kotlin中默认变量不可为空 所以类型声明时必须初始化

但是依然可以声明可空类型

> var like:String? = null
>
> 在类型后面加问号

 可空类型特点

1. 可空类型必须声明类型 可空类型无法使用类型推断
2. 可空类型调用时必须使用安全调用或者强引调用

```kotlin
var name:String? = "mike"
//安全调用
var length = name?.length
//强调用 为空则抛出异常
var length2 = name!!.length
```



### 3. 类型初始化

1. 直接赋值 var age = 2

2. 延迟初始化 lateinit var age **注意延迟初始化kotlin会脱离kotlin的空安全设计 调用时需要考虑空指针问题**

3. 属性委托初始化by lazy 属性委托自行百度 **by lazy 只适用于val常量属性 想知道为什么自行查看源码**

   ```
   val age by lazy { 12 }
   ```

   
## 方法声明

### 格式

> fun 方法名(变量:类型):返回值{}

   例

``` kotlin
fun numAdd(a:Int, b:Int):Int{
  return a + b
}

fun printNum(a:Int):Unit{
  print("num=$a")
}
fun numDuct(a:Int, b:Int) = a-b
```

- 方法如果无返回值时 Unit可以省略
- 有返回值的函数体内逻辑只有一行时 可以省略大括号 直接=加逻辑

### 函数默认值

​	在java中可以对方法进行重载 而在kotlin中没有重载概念 但是增加方法默认值处理

#### java 重载

```java
public void log(String tag,String msg){
  Log.i(tag,msg);
}
public void log(String msg){
  Log.i("TAG",msg);
}
```

#### kotlin参数默认值

```kotlin
fun log(tag:String = "TAG",msg:String){
  Log.i(tag,msg)
}
```

- 上面代码实现的效果是一样的 但是如果java调kotlin有参数默认值函数的时候 需要加入**@JvmOverloads**注解来让编辑成java文件的时候生成多个函数体 否则只会生成一个函数
- 如果函数开头便有默认参数 则调用方法时需要指定赋值的参数值 例：log(msg = "test")

## Kotlin类

- kotlin中的类的默认父类都是Any而不是Object
- kotlin中的类和函数默认都是final修饰的 如果需要继承 需要增加open修饰符
- kotlin中继承需要实现默认主构造器函数 
- 对于public变量 会默认实现get set 函数
- 静态方法声明使用companion object 伴生对象

### 1.普通类

```kotlin

//声明类及主构造器
open class UserManager constructor(var name:String , var age:Int,var like:String?){
  
  companion object{
    //内部声明的变量和函数全部为静态
    
    //编译期常量
    const val TAG = "TAG" 
    var state = 1
    //静态方法
    fun log(){}
  }
  
  init{
    //初始化主构造器
    
    //这里会报错 kotlin执行为一行一行执行 调用test方法时 test函数还未声明 解决方案 test函数放到上面
    test()
  }
  //次构造器必须调用主构造器函数
  constructor(name:String, age:Int):this(name,age,null){
    
  }
  
  private fun test(){
    
  }
  
  open fun release(){
    
  }
}

//继承类时必须实现继承类主构造器
class UserManagerWrapper( name:String ,  age:Int, like:String?):UserManager(name,age,like){
 
  
  //重写父类方法 使用override关键字
  override fun release(){
    super()
  }
  
}
```



### 2.数据类

kotlin新增了数据类 专门存储数据 无任何逻辑操作

- 数据类使用 data class声明
- 默认实现了 hashCode equals  toString函数
- 默认新增实现了copy(新增函数 用于对象拷贝)  解构函数（不清楚的查看官方文档）

``` kotlin
data class User(var name:String,val age:Int)
```



### 3.接口

- 和java无区别
- 新增了抽象变量

```kotlin
interface testCall{
  fun call()
  val naem:String
  var age:Int
}
```

### 4.抽象类

- 无区别

```kotlin
abstract class AbstractTest{
 fun test()
}
```

### 5.单例类（新增）

- 单例 kotlin保证线程安全

- 不可设置构造器

  ```kotlin
  object TestSingle{
   fun test()
  }
  
  fun main(){
  TestSingle.test()
  }
  ```

  



