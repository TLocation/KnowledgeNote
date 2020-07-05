### 自定义属性定义过程：

1. 在 values 中的 attr 文件声明所需的参数
2. 然后 rebuild 一下
3. 在 xml 布局中添加资源引用地址
4. 在 xml 的 view 标签中使用
5. 在 view 中解析自定义属性，获取参数

### attr 文件

attr 是 values 目录下的资源文件

```layout
    <declare-styleable name="MyView">
        <attr name="name" format="string"></attr>
        <attr name="info" format="float"></attr>
    </declare-styleable>
```

![img](https://upload-images.jianshu.io/upload_images/1785445-249517fb411792c3.png?imageMogr2/auto-orient/strip|imageView2/2/w/621/format/webp)

- boolean
   表示attr取值为true或者false

- color
   表示attr取值是颜色类型，例如#ff3344,或者是一个指向color的资源id，例如R.color.colorAccent.

- dimension
   表示 attr 取值是尺寸类型，例如例如取值16sp、16dp，也可以是一个指向dimen的资源id，例如R.dimen.dp_16

- float
   表示attr取值是整形或者浮点型

- fraction
   表示 attr取值是百分数类型，只能以%结尾，例如30%

- integer
   表示attr取值是整型

- string
   表示attr取值是String类型，或者一个指向String的资源id，例如R.string.testString

- reference
   表示attr取值只能是一个指向资源的id。

- enum
   表示attr取值只能是枚举类型。

- flag
   表示attr取值是flag类型。

- color | reference
   加 | 表示这个属性可以接受2种参数

### view 中获取自定义属性值

```xml
String name = typedArray.getString(R.styleable.MyView_name);
```

自定义属性的 id 是系统自动生成的，id = 自定义属性组名 + "_" + 自定义属性名

### attr 的命名空间

+应用包名的

```xml
xmlns:myview="http://schemas.android.com/apk/com.bloodcrown.aaa02"
```

系统自带的属性后面直接跟 android

```xml
xmlns:android="http://schemas.android.com/apk/res/android"
```

推荐使用  写一个命名空间，可以包含所有的自定义属性

```xml
xmlns:app="http://schemas.android.com/apk/res-auto"
```

### 使用系统定义的 xml 属性

定义 xml 属性时使用 android： 前缀

```xml
 <declare-styleable name="MyView">
     <attr name="android:textColor" tools:ignore="ResourceName" />
        <attr name="android:textSize" tools:ignore="ResourceName" />
 </declare-styleable>    
```

获取自定义属性

```java
 textColor = arr.getColor(R.styleable.RollingTextView_android_textColor, textColor)
 textSize = arr.getDimension(R.styleable.RollingTextView_android_textSize, textSize)
```

```
<com.example.sweepgradientdemo.MyView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:info="12.0"
    app:name="SweepGradientView"
    android:textSize="20dp"/>
```

### 获取自定义 view 中的 style

```java
TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextViewApprence, defStyleAttr, 0);
mTextSize = (int) typedArray.getDimension(R.styleable.MyView_textSize, 15);
```

