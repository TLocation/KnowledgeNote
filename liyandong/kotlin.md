#### Kotlin

1. Kotlin 是一种在 Java 虚拟机上运行的静态类型编程语言，被称之为 Android 世界的Swift，由 JetBrains 设计开发并开源。

2. Kotlin 程序文件以 **.kt** 结尾，如：hello.kt 

3. Kotlin 最简版

   ```
   最简版
   package hello                      //  可选的包头
    
   fun main(args: Array<String>) {    // 包级可见的函数，接受一个字符串数组作为参数
      println("Hello World!")         // 分号可以省略
   }
   ```

#### Kotlin特点

1. 大大减少样板代码的数量。
2. 避免了空指针异常等造成整个类的错误。
3. 用于  服务端  Android  JavaScript 协程 多平台开发

#### 变量的声明与赋值

Java 里声明一个 View 类型的变量的写法：

```java
String s;
```

Kotlin 里声明一个变量的格式的：

```kotlin
var s: String
```

这里有几处不同：

- 有一个 `var` 关键字

- 类型和变量名位置互换了

- 中间是用冒号分隔的

- 结尾没有分号（对，Kotlin 里面不需要分号）

  

```kotlin
class Test {
    var s: String
    // 👆这样写 IDE 会报如下错误
    // Property must be initialized or be abstract
}
```

这个提示是在说，属性需要在声明的同时初始化，除非你把它声明成抽象的。

属性为什么要求初始化呢？因为 Kotlin 的变量是没有默认值的，这点不像 Java

既然这样，那我们就给它一个默认值 null 吧，遗憾的是你会发现仍然报错。

```kotlin
class Sample {
    var s: String = null
    // 👆这样写 IDE 仍然会报错，Null can not be a value of a non-null type View
}
```

提示需要赋一个非空的值给它才行

原因：Kotlin 的空安全设计

### Kotlin 的空安全设计

在 Kotlin 里面，所有的变量默认都是不允许为空的，如果你给它赋值 null，就会报错，像上面那样。

这种有点强硬的要求，其实是很合理的：既然你声明了一个变量，就是要使用它对吧？那你把它赋值为 null 干嘛？要尽量让它有可用的值啊。Java 在这方面很宽松，我们成了习惯，但 Kotlin 更强的限制其实在你熟悉了之后，**是会减少很多运行时的问题的**。

有些场景，变量的值真的无法保证空与否，比如你要从服务器取一个 JSON 数据，并把它解析成一个 User 对象：

```kotlin
class User {
    var name: String = null // 👈这样写会报错，但该变量无法保证空与否
}
```

这个时候，空值就是有意义的。对于这些可以为空值的变量，你可以在类型右边加一个 `?` 号，解除它的非空限制

```kotlin
class User {
    var name: String? = null
}
```

这种类型之后加 `?` 的写法，在 Kotlin 里叫**可空类型**。

不过，当我们使用了可空类型的变量后，会有新的问题：

```kotlin
var view: View? = null
view.setBackgroundColor(Color.RED)
// 👆这样写会报错，Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type View?
```

这种情况会遇到可能为空的变量，那么 Kotlin 里是这么解决这个问题的呢？它用的不是 `.` 而是 `?.`：

```kotlin
view?.setBackgroundColor(Color.RED)
```

另外还有一种双感叹号的用法：

```kotlin
view!!.setBackgroundColor(Color.RED)
```

意思是告诉编译器，我保证这里的 view 一定是非空的，编译器你不要帮我做检查了，有什么后果我自己承担。这种「肯定不会为空」的断言式的调用叫做 「**non-null asserted call**」。一旦用了非空断言，实际上和 Java 就没什么两样了，但也就享受不到 Kotlin 的空安全设计带来的好处（在编译时做检查，而不是运行时抛异常）了。

关于空安全，最重要的是记住一点：所谓「可空不可空」，关注的全都是使用的时候，即「**这个变量在使用时是否可能为空**」。

#### 延迟初始化

```kotlin
lateinit var view: View
```

这个 `lateinit` 的意思是：告诉编译器我没法第一时间就初始化，但我肯定会在使用它之前完成初始化的。

#### 类型推断

```kotlin
var name: String = "Mike"
👇
var name = "Mike"
```

在声明的时候就赋值，那不写变量类型也行

#### val（value ） 和 var （variable ）

```kotlin
val age = 18
```

val 是 Kotlin 在 Java 的「变量」类型之外，又增加的一种变量类型：只读变量。它只能赋值一次，不能修改。而 var 是一种可读可写变量。

### 函数的声明

有返回值

```kotlin
fun eat(name: String): String {
    ...
}
```

- 以 fun 关键字开头
- 返回值写在了函数和参数后面

无返回值

```kotlin
fun main(): Unit {}
// Unit 返回类型可以省略
fun main() {}
```

函数参数也可以有可空，不可空的控制，根据前面说的空安全设计，在传递时需要注意：

```kotlin
// 👇可空变量传给可空参数，正常运行
var myName : String? = "rengwuxian"
fun eat(name: String?) : String {}
eat(myName)

// 👇不可空变量传给不可空参数，正常运行
var myName : String = "rengwuxian"
fun eat(name: String) : String {}
eat(myName)
```

### 类和对象

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ...
    }
}
```

1. 类的可见性，Java 中的 public 在 Kotlin 中可以省略，Kotlin 的类默认是 public 的。
2. 类的继承的写法：Java 里用的是 `extends`，而在 Kotlin 里使用 `:`，但其实 `:` 不仅可以表示继承，还可以表示 Java 中的 `implement`。

```kotlin
interface Impl {}
```

```kotlin
class MainActivity : AppCompatActivity(), Impl {}
```

   3.构造方法的写法不同。

Kotlin 里我们注意到 AppCompatActivity 后面的 `()`，这其实也是一种省略的写法，等价于：

```kotlin
 class MainActivity : AppCompatActivity() {
                          👆
  }
```

   4.override 的不同

- Java 里面 `@Override` 是注解的形式。
- Kotlin 里的 `override` 变成了关键字。
- Kotlin 省略了 `protected` 关键字，也就是说，Kotlin 里的 `override` 函数的可见性是继承自父类的。

   5.类的实例化

```kotlin
fun main() {
    var activity: Activity = NewActivity()
}
```

   6.类的属性get.set方法

先看一下java对set和get的实现

```cpp
class Student{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
// 所以，一般设置一个参数，都用以下方式实现。
Student stu = new Student();
stu.setName("小明");
```

kotlin的实现方式

```kotlin
class Student{
    val name = ""
}
// 设置参数
val stu = Student()
stu.name = "小明"
```

使用类似kotlin的实现方式stu.name = 小明的时候，其实是调用了默认的setter方法。当然val声明不允许实现setter方法。



## 判断两个对象相等

Java中，判断两个对象的地址，或者两个对象是否相等，分别使用equals和==

kotlin中，稍微做了一点改动，kotlin中的==等同于java中的equals，kotlin中的===等同于java中的==

### 类型的判断和强转

使用 `is` 关键字进行「类型判断」

```kotlin
fun main() {
    var activity: Activity = NewActivity()
    if (activity is NewActivity) {
        // 👇的强转由于类型推断被省略了
        activity.action()
    }
}
```

能不能不进行类型判断，直接进行强转调用呢？可以使用 `as` 关键字：

```kotlin
fun main() {
    var activity: Activity = NewActivity()
    (activity as NewActivity).action()
}
```

### `object`

Java 中的 `Object` 在 Kotlin 中变成了 `Any`，和 `Object` 作用一样：作为所有类的基类。

Kotlin 里的 `object`不是类，像 `class` 一样在 Kotlin 中属于关键字：

```kotlin
object Sample {
    val name = "A name"
}
```

创建一个类，并且创建一个这个类的对象。这个就是 `object` 的意思：对象

使用

```kotlin
Sample.name
```

单例类

Java 中实现单例类（非线程安全）：

```java
public class A {
    private static A sInstance;
    
    public static A getInstance() {
        if (sInstance == null) {
            sInstance = new A();
        }
        return sInstance;
    }

    // 👇还有很多模板代码
    ...
}
```

Kotlin 中实现单例类：

```kotlin
object A {
    val number: Int = 1
    fun method() {
        println("A.method()")
    }
}    
```

### `companion object` 伴生对象

```kotlin
class A {
       👇
    companion object B {
        var c: Int = 0
    }
}
```

`companion` 可以理解为伴随、伴生，表示修饰的对象和外部类绑定。

一个小限制：一个类中最多只可以有一个伴生对象，但可以有多个嵌套对象。就像皇帝后宫佳丽三千，但皇后只有一个。

调用：

```kotlin
A.c // 👈 B 没了
```

```kotlin
class A {
                // 👇 B 没了
    companion object {
        var c: Int = 0
    }
}
```

当有 `companion` 修饰时，对象的名字也可以省略掉，Java 静态变量和方法的等价写法：`companion object` 变量和函数。

### 数组和集合

声明一个 String 数组：



```java
String[] strs = {"a", "b", "c"};  java写法
```

```kotlin
val strs: Array<String> = arrayOf("a", "b", "c")  kotlin写法
```

### Kotlin 和 Java 一样有三种集合类型：List、Set 和 Map，它们的含义分别如下：

- `List` 以固定顺序存储一组元素，元素可以重复。
- `Set` 存储一组互不相等的元素，通常没有固定顺序。
- `Map` 存储 键-值 对的数据集合，键互不相等，但不同的键可以对应相同的值。

List

- Java 中创建一个列表：

  ```java
  ☕️
  List<String> strList = new ArrayList<>();
  strList.add("a");
  strList.add("b");
  strList.add("c"); // 👈 添加元素繁琐
  ```

- Kotlin 中创建一个列表：

  ```kotlin
  🏝️            
  val strList = listOf("a", "b", "c")
  ```

Set

- Java 中创建一个 `Set`：

  ```java
  ☕️
  Set<String> strSet = new HashSet<>();
  strSet.add("a");
  strSet.add("b");
  strSet.add("c");
  ```

- Kotlin 中创建相同的 `Set`：

  ```kotlin
  🏝️           
  val strSet = setOf("a", "b", "c")
  ```

Map

- Java 中创建一个 `Map`：

  ```java
  ☕️
  Map<String, Integer> map = new HashMap<>();
  map.put("key1", 1);
  map.put("key2", 2);
  map.put("key3", 3);
  map.put("key4", 3);
  ```

- Kotlin 中创建一个 `Map`：

  ```kotlin
  🏝️         
  val map = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 3)
  ```

## **可见性修饰符**

Kotlin 中有四种可见性修饰符：

- `public `：公开，可见性最大，哪里都可以引用。
- `private`：私有，可见性最小，根据声明位置不同可分为类中可见和文件中可见。
- `protected`：保护，相当于 `private` + 子类可见。
- `internal`：内部，仅对 module 内可见。

比 Java 少了一个 `default` 「包内可见」修饰符，多了一个 `internal`「module 内可见」修饰符

### `public`

Kotlin 中如果不写可见性修饰符，就表示公开，和 Java 中 `public` 修饰符具有相同效果。在 Kotlin 中 `public` 修饰符「可以加，但没必要」。

### `private`

- Java 中的 `private` 表示类中可见，作为内部类时对外部类「可见」。
- Kotlin 中的 `private` 表示类中或所在文件内可见，作为内部类时对外部类「不可见」。

请注意在 Kotlin 中，外部类不能访问内部类的 private 成员。

### `protected`

- Java 中 `protected` 表示包内可见 + 子类可见。
- Kotlin 中 `protected` 表示 `private` + 子类可见。

Kotlin 相比 Java `protected` 的可见范围收窄了，原因是 Kotlin 中不再有「包内可见」的概念了，相比 Java 的可见性着眼于 `package`，Kotlin 更关心的是 module。

### `internal`

`internal` 表示修饰的类、函数仅对 module 内可见，