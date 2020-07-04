# Kotlin中的类

1. 类中的构造器 `constructor`

   类声明由类名、类头（指定其类型参数、主构造函数等）以及由花括号包围的类体构成。类头与类体都是可选的； 如果一个类没有类体，可以省略花括号。

   ### 构造函数

   在 Kotlin 中的一个类可以有一个**主构造函数**以及一个或多个**次构造函数**。主构造函数是类头的一部分：它跟在类名（与可选的类型参数）后。如果主构造函数没有任何注解或者可见性修饰符，可以省略这个 *constructor* 关键字。

   ```kotlin
   class Person(firstName: String) { /*……*/ }
   ```

   事实上，声明属性以及从主构造函数初始化属性，Kotlin 有简洁的语法：

    class Person(val firstName: String, val lastName: String, var age: Int) { }

   这样的写完等同于下面这种写法。

   class Person(){

   val fistName:String? = null

   }

   #### 次构造函数

​         如果类有一个主构造函数，每个次构造函数需要委托给主构造函数， 可以直接委托或者通过别的次构造函数间接委托。委托到同一个类的另一个构造函数用 *this* 关键字即可：

```kotlin
open class Person (val name :String,var sex: String,var age: Int){
    
    constructor(name :String,sex :String) :this(name,sex,0){

    }
    constructor(name :String) :this(name,"",0){
   } 
    }
```

请注意，初始化块中的代码实际上会成为主构造函数的一部分。委托给主构造函数会作为次构造函数的第一条语句，因此所有初始化块与属性初始化器中的代码都会在次构造函数体之前执行。即使该类没有主构造函数，这种委托仍会隐式发生，并且仍会执行初始化块：

```kotlin
class Constructors {
    init {
        println("Init block")
    }

 constructor(i: Int) {
        println("Constructor")
    }
}  // 所以这里会先执行 初始化代码块内输出语句。
```



## 继承

在 Kotlin 中所有类都有一个共同的超类 `Any`，这对于没有超类型声明的类是默认超类；                                               class Example // 从 Any 隐式继承                                                                                                                                              `Any` 有三个方法：`equals()`、 `hashCode()` 与 `toString()`。因此，为所有 Kotlin 类都定义了这些方法                               默认情况下，Kotlin 类是最终（final）的：它们不能被继承。 要使一个类可继承，请用 `open` 关键字标记它。                                     open class Base // 该类开放继承

如需声明一个显式的超类型，请在类头中把超类型放到冒号之后：

```kotlin
open class Base(p: Int)

class Derived(p: Int) : Base(p)
```

如果派生类有一个主构造函数，其基类可以（并且必须） 用派生类主构造函数的参数就地初始化。

如果派生类没有主构造函数，那么每个次构造函数必须使用 *super* 关键字初始化其基类型，或委托给另一个构造函数做到这一点。 注意，在这种情况下，不同的次构造函数可以调用基类型的不同的构造函数：

```kotlin
class MyView : View {
    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
}
```



## **嵌套类与内部类**

类可以嵌套在其他类中：

```kotlin
//嵌套类
kclass Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo() // == 2
```

标记为 *inner* 的嵌套类能够访问其外部类的成员。内部类会带有一个对外部类的对象的引用

```kotlin
//内部类
class Outer {
    private val bar: Int = 1
    inner class Inner {
        fun foo() = bar
    }
}

val demo = Outer().Inner().foo() // == 1
```

## 接口

Kotlin 的接口可以既包含抽象方法的声明也包含实现。与抽象类不同的是，接口无法保存状态。它可以有属性但必须声明为抽象或提供访问器实现。

使用关键字 *interface* 来定义接口

```kotlin
interface MyInterface {
    fun bar()
    fun foo() {
      // 可选的方法体
    }
}
```

### 实现接口

一个类或者对象可以实现一个或多个接口。

```kotlin
class Child : MyInterface {
    override fun bar() {
        // 方法体
    }
}
```



## 接口中的属性

你可以在接口中定义属性。在接口中声明的属性要么是抽象的，要么提供访问器的实现。在接口中声明的属性不能有幕后字段（backing field），因此接口中声明的访问器不能引用它们。

```kotlin
interface MyInterface {
    val prop: Int // 抽象的

    val propertyWithImplementation: String
        get() = "foo"

    fun foo() {
        print(prop)
    }
}

class Child : MyInterface {
    override val prop: Int = 29
}
```



## 解决覆盖冲突

实现多个接口时，可能会遇到同一方法继承多个实现的问题。例如

```kotlin
interface A {
    fun foo() { print("A") }
    fun bar()
}

interface B {
    fun foo() { print("B") }
    fun bar() { print("bar") }
}

class C : A {
    override fun bar() { print("bar") }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}
```

上例中，接口 *A* 和 *B* 都定义了方法 *foo()* 和 *bar()*。 两者都实现了 *foo()*, 但是只有 *B* 实现了 *bar()* (*bar()* 在 *A* 中没有标记为抽象， 因为没有方法体时默认为抽象）。因为 *C* 是一个实现了 *A* 的具体类，所以必须要重写 *bar()* 并实现这个抽象方法。

然而，如果我们从 *A* 和 *B* 派生 *D*，我们需要实现我们从多个接口继承的所有方法，并指明 *D* 应该如何实现它们。这一规则既适用于继承单个实现（*bar()*）的方法也适用于继承多个实现（*foo()*）的方法。

## 数据类与密封类

Kotlin 可以创建一个只包含数据的类，关键字为 **data**：

```kotlin
data class User(val name: String, val age: Int)
```

编译器会自动的从主构造函数中根据所有声明的属性提取以下函数：

- `equals()` / `hashCode()`
- `toString()` 格式如 `"User(name=John, age=42)"`
- `componentN() functions` 对应于属性，按声明顺序排列
- `copy()` 函数

如果这些函数在类中已经被明确定义了，或者从超类中继承而来，就不再会生成。

为了保证生成代码的一致性以及有意义，数据类需要满足以下条件：

- 主构造函数至少包含一个参数。
- 所有的主构造函数的参数必须标识为`val` 或者 `var` ;
- 数据类不可以声明为 `abstract`, `open`, `sealed` 或者 `inner`;
- 数据类不能继承其他类 (但是可以实现接口)。

### 复制

复制使用 copy() 函数，我们可以使用该函数复制对象并修改部分属性, 对于上文的 User 类，其实现会类似下面这样：

```kotlin
fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
```

### 实例

使用 copy 类复制 User 数据类，并修改 age 属性:

```kotlin
data class User(val name: String, val age: Int)


fun main(args: Array<String>) {
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    println(jack)
    println(olderJack)

}
```

## 密封类

密封类用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时。在某种意义上，他们是枚举类的扩展：枚举类型的值集合 也是受限的，但每个枚举常量只存在一个实例，而密封类 的一个子类可以有可包含状态的多个实例。

声明一个密封类，使用 **sealed** 修饰类，密封类可以有子类，但是所有的子类都必须要内嵌在密封类中。

sealed 不能修饰 interface ,abstract class(会报 warning,但是不会出现编译错误)

```kotlin
    /**
     * 密封类
     */
fun main{
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
```

## 枚举类

枚举类最基本的用法是实现一个类型安全的枚举。

枚举常量用逗号分隔,每个枚举常量都是一个对象。

```kotlin
enum class Color{
    RED,BLACK,BLUE,GREEN,WHITE
}
```

### 枚举初始化

每一个枚举都是枚举类的实例，它们可以被初始化：

```kotlin
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}
```

默认名称为枚举字符名，值从0开始。若需要指定值，则可以使用其构造函数：

```kotlin
enum class Shape(value:Int){
    ovel(100),
    rectangle(200)
}
```

枚举还支持以声明自己的匿名类及相应的方法、以及覆盖基类的方法。如：

```kotlin
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}
```

如果枚举类定义任何成员，要使用分号将成员定义中的枚举常量定义分隔开

------

## 使用枚举常量

Kotlin 中的枚举类具有合成方法，允许遍历定义的枚举常量，并通过其名称获取枚举常数。

```kotlin
EnumClass.valueOf(value: String): EnumClass  // 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
EnumClass.values(): Array<EnumClass>        // 以数组的形式，返回枚举值
```

获取枚举相关信息：

```kotlin
val name: String //获取枚举名称
val ordinal: Int //获取枚举值在所有枚举数组中定义的顺序
```

### 实例

```kotlin
enum class Color{
    RED,BLACK,BLUE,GREEN,WHITE
}

fun main(args: Array<String>) {
    var color:Color=Color.BLUE

    println(Color.values())
    println(Color.valueOf("RED"))
    println(color.name)
    println(color.ordinal)

}
```