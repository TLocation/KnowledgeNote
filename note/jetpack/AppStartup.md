#####  AppStartup入门指南

**startup** 是一个帮助Library初始化工程的一个库

引用google原文

> The App Startup library provides a straightforward, performant way to initialize components at application startup. Both library developers and app developers can use App Startup to streamline startup sequences and explicitly set the order of initialization.
>
> Instead of defining separate content providers for each component you need to initialize, App Startup allows you to define component initializers that share a single content provider. This can significantly improve app startup time

这段话的意思是

google提供了一套api（接口） 可以在应用启动时按顺序来初始化组件 而不需要每个组件单独写一套初始化代码 从而缩短app启动时间



#### 一 导入依赖

```groovy
    implementation "androidx.startup:startup-runtime:1.0.0-alpha01"

```

目前还只是alpha版本 只能测试使用 还不能用于生产

#### 二 在需要初始化组件的library实现Initializer接口

```kotlin
class LogInit : Initializer<String> {
    //初始化时调用
    override fun create(context: Context): String {
        //初始化逻辑
        debugMsg("LogInit init create")
         return  "value"
    }

    //返回这个组件还依赖的其他组件的初始化Initializer配置 如果A依赖B 那么这里还需要返回B的初始化Initializer
    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        debugMsg("LogInit init dependencies")
       return mutableListOf(NetWorkInit::class.java)
    }
}

fun Any.debugMsg(msg:String){
    Log.i("TestStartup",msg)
}
class NetWorkInit:Initializer<String>{
    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
     return mutableListOf()
    }

    override fun create(context: Context): String {
        debugMsg("NetWorkInit Initializer")
        return "networkInit"
    }

}
```



调用顺序

1. 首先会调用**dependencies**函数 如果集合不等于空 会继续调用集合内的**Initializer**实现类 最后调用本类的**create**函数

#### 二配置manifest

在各自的modle manifest下面配置如下代码

```xml
 <provider
            android:name="androidx.startup.InitializationProvider"
            android:authorities="${applicationId}.androidx-startup"
            android:exported="false"
            tools:node="merge">
            <!--name 初始化的类包名-->
            <meta-data  android:name="com.location.logmodle.LogInit"
                android:value="androidx.startup"
           />
        </provider>
```

配置说明

- tools:node 是否参与manifest合并

- 在meta-data下面配置tools:node="remove" 会停止初始化该meta的配置类 需要进行手动初始化

- 手动初始化 

  ```kotlin
  AppInitializer.getInstance(context)
      .initializeComponent(class)
  ```



上线这些配置完之后就ok了 直接运行应用 就会自动调用meta-data配置的初始化类

